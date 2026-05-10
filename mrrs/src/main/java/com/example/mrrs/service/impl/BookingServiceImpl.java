package com.example.mrrs.service.impl;

import com.example.mrrs.dto.booking.BookingRequest;
import com.example.mrrs.dto.booking.BookingResponse;

import com.example.mrrs.entity.*;

import com.example.mrrs.entity.BookingStatus;

import com.example.mrrs.mapper.BookingMapper;

import com.example.mrrs.repository.*;

import com.example.mrrs.service.BookingService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl
        implements BookingService {

    private final BookingRepository bookingRepository;

    private final BookingSeatRepository bookingSeatRepository;

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleRepository;

    private final SeatRepository seatRepository;

    private final BookingMapper bookingMapper;

    // =========================================
    // CREATE BOOKING
    // =========================================
    @Override
    @Transactional
    public BookingResponse createBooking(
            BookingRequest request
    ) {

        // =====================================
        // FIND USER
        // =====================================
        User user = userRepository.findById(
                request.getUserId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "User not found"
                ));

        // =====================================
        // FIND SCHEDULE
        // =====================================
        Schedule schedule =
                scheduleRepository.findById(
                        request.getScheduleId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Schedule not found"
                        ));

        // =====================================
        // FIND SEATS
        // =====================================
        List<Seat> seats =
                seatRepository.findAllById(
                        request.getSeatIds()
                );

        // =====================================
        // VALIDATE SEATS
        // =====================================
        if (seats.isEmpty()) {

            throw new RuntimeException(
                    "No seats selected"
            );
        }

        for (Seat seat : seats) {

            if (!seat.getIsAvailable()) {

                throw new RuntimeException(
                        "Seat "
                                + seat.getSeatNumber()
                                + " already booked"
                );
            }
        }

        // =====================================
        // CALCULATE TOTAL AMOUNT
        // =====================================
        BigDecimal totalAmount =
                schedule.getPrice().multiply(
                        BigDecimal.valueOf(
                                seats.size()
                        )
                );

        // =====================================
        // CREATE BOOKING
        // =====================================
        Booking booking = Booking.builder()
                .user(user)
                .schedule(schedule)
                .bookingDate(LocalDateTime.now())
                .status(BookingStatus.CONFIRMED)
                .totalAmount(totalAmount)
                .build();

        bookingRepository.save(booking);

        // =====================================
        // CREATE BOOKING SEATS
        // =====================================
        List<BookingSeat> bookingSeats =
                new ArrayList<>();

        for (Seat seat : seats) {

            BookingSeat bookingSeat =
                    BookingSeat.builder()
                            .booking(booking)
                            .seat(seat)
                            .build();

            bookingSeats.add(bookingSeat);

            // UPDATE SEAT STATUS
            seat.setIsAvailable(false);
        }

        bookingSeatRepository.saveAll(
                bookingSeats
        );

        seatRepository.saveAll(seats);

        // =====================================
        // RETURN RESPONSE
        // =====================================
        return bookingMapper.toResponse(
                booking,
                bookingSeats
        );
    }

    // =========================================
    // GET BOOKING BY ID
    // =========================================
    @Override
    public BookingResponse getBookingById(
            Long bookingId
    ) {

        Booking booking =
                bookingRepository.findById(
                        bookingId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Booking not found"
                        ));

        List<BookingSeat> bookingSeats =
                bookingSeatRepository
                        .findByBookingBookingId(
                                bookingId
                        );

        return bookingMapper.toResponse(
                booking,
                bookingSeats
        );
    }

    // =========================================
    // GET ALL BOOKINGS
    // =========================================
    @Override
    public List<BookingResponse> getAllBookings() {

        List<Booking> bookings =
                bookingRepository.findAll();

        return bookings.stream().map(booking -> {

            List<BookingSeat> bookingSeats =
                    bookingSeatRepository
                            .findByBookingBookingId(
                                    booking.getBookingId()
                            );

            return bookingMapper.toResponse(
                    booking,
                    bookingSeats
            );

        }).toList();
    }

    // =========================================
    // GET BOOKINGS BY USER
    // =========================================
    @Override
    public List<BookingResponse> getBookingsByUser(
            Long userId
    ) {

        List<Booking> bookings =
                bookingRepository
                        .findByUserUserId(
                                userId
                        );

        return bookings.stream().map(booking -> {

            List<BookingSeat> bookingSeats =
                    bookingSeatRepository
                            .findByBookingBookingId(
                                    booking.getBookingId()
                            );

            return bookingMapper.toResponse(
                    booking,
                    bookingSeats
            );

        }).toList();
    }

    @Override
    public List<BookingResponse> getBookingsBySchedule(
            Long scheduleId
    ) {

        List<Booking> bookings =
                bookingRepository
                        .findByScheduleScheduleId(
                                scheduleId
                        );

        return bookings.stream().map(booking -> {

            List<BookingSeat> bookingSeats =
                    bookingSeatRepository
                            .findByBookingBookingId(
                                    booking.getBookingId()
                            );

            return bookingMapper.toResponse(
                    booking,
                    bookingSeats
            );

        }).toList();
    }

    // =========================================
    // CANCEL BOOKING
    // =========================================
    @Override
    @Transactional
    public void cancelBooking(
            Long bookingId
    ) {

        Booking booking =
                bookingRepository.findById(
                        bookingId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Booking not found"
                        ));

        // =====================================
        // GET BOOKED SEATS
        // =====================================
        List<BookingSeat> bookingSeats =
                bookingSeatRepository
                        .findByBookingBookingId(
                                bookingId
                        );

        // =====================================
        // RELEASE SEATS
        // =====================================
        for (BookingSeat bookingSeat : bookingSeats) {

            Seat seat = bookingSeat.getSeat();

            seat.setIsAvailable(true);

            seatRepository.save(seat);
        }

        // =====================================
        // UPDATE BOOKING STATUS
        // =====================================
        booking.setStatus(
                BookingStatus.CANCELLED
        );

        bookingRepository.save(booking);
    }
}