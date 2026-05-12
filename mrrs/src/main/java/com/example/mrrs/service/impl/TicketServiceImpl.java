package com.example.mrrs.service.impl;


import com.example.mrrs.dto.ticket.TicketResponse;
import com.example.mrrs.dto.ticket.TicketValidationResponse;
import com.example.mrrs.entity.Booking;
import com.example.mrrs.entity.Payment;
import com.example.mrrs.entity.Ticket;
import com.example.mrrs.entity.BookingStatus;
import com.example.mrrs.entity.PaymentStatus;
import com.example.mrrs.entity.TicketStatus;
import com.example.mrrs.exception.ResourceNotFoundException;
import com.example.mrrs.repository.BookingRepository;
import com.example.mrrs.repository.TicketRepository;
import com.example.mrrs.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    @Override
    public TicketResponse generateTicket(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        if (ticketRepository.existsByBooking_BookingId(bookingId)) {
            throw new RuntimeException("Ticket already generated");
        }

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Booking is not confirmed");
        }

        Payment payment = booking.getPayment();

        if (payment == null ||
                payment.getPaymentStatus() != PaymentStatus.SUCCESS) {

            throw new RuntimeException("Payment not successful");
        }

        Ticket ticket = Ticket.builder()
                .booking(booking)
                .pnrNumber(generatePnrNumber())
                .qrCode(null)
                .issuedAt(LocalDateTime.now())
                .status(TicketStatus.ACTIVE)
                .build();

        ticketRepository.save(ticket);

        return mapToResponse(ticket);
    }

    @Override
    public TicketResponse getTicketByPnr(String pnrNumber) {

        Ticket ticket = ticketRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        return mapToResponse(ticket);
    }

    @Override
    public List<TicketResponse> getAllTickets() {

        return ticketRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TicketValidationResponse validateTicket(String pnrNumber) {

        Ticket ticket = ticketRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        if (ticket.getStatus() == TicketStatus.CANCELLED) {

            return TicketValidationResponse.builder()
                    .pnrNumber(pnrNumber)
                    .valid(false)
                    .message("Ticket cancelled")
                    .build();
        }

        if (ticket.getStatus() == TicketStatus.USED) {

            return TicketValidationResponse.builder()
                    .pnrNumber(pnrNumber)
                    .valid(false)
                    .message("Ticket already used")
                    .build();
        }

        return TicketValidationResponse.builder()
                .pnrNumber(pnrNumber)
                .valid(true)
                .message("Ticket valid")
                .build();
    }

    @Override
    public void markTicketAsUsed(String pnrNumber) {

        Ticket ticket = ticketRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.USED);

        ticketRepository.save(ticket);
    }

    @Override
    public void cancelTicket(String pnrNumber) {

        Ticket ticket = ticketRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.CANCELLED);

        ticketRepository.save(ticket);
    }

    private String generatePnrNumber() {

        return "PNR-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    private TicketResponse mapToResponse(Ticket ticket) {

        Booking booking = ticket.getBooking();

        return TicketResponse.builder()
                .ticketId(ticket.getTicketId())
                .pnrNumber(ticket.getPnrNumber())
                .bookingId(booking.getBookingId())

                .passengerName(
                        booking.getUser().getFullName()
                )

                .trainName(
                        booking.getSchedule()
                                .getTrain()
                                .getTrainName()
                )

                .departureTime(
                        LocalDateTime.from(booking.getSchedule()
                                .getDepartureTime())
                )

                .arrivalTime(
                        LocalDateTime.from(booking.getSchedule()
                                .getArrivalTime())
                )

                .totalAmount(
                        booking.getTotalAmount()
                )

                .status(
                        ticket.getStatus().name()
                )

                .issuedAt(
                        ticket.getIssuedAt()
                )

                .build();
    }
}