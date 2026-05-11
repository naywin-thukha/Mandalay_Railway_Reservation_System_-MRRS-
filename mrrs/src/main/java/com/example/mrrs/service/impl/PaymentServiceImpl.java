package com.example.mrrs.service.impl;

import com.example.mrrs.dto.payment.PaymentRequest;
import com.example.mrrs.dto.payment.PaymentResponse;
import com.example.mrrs.entity.Booking;
import com.example.mrrs.entity.Payment;
import com.example.mrrs.entity.BookingStatus;
import com.example.mrrs.entity.PaymentStatus;
import com.example.mrrs.repository.BookingRepository;
import com.example.mrrs.repository.PaymentRepository;
import com.example.mrrs.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        // Prevent duplicate payment
        if (paymentRepository.findByBookingBookingId(
                booking.getBookingId()).isPresent()) {

            throw new RuntimeException("Payment already exists");
        }

        Payment payment = Payment.builder()
                .booking(booking)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.SUCCESS)
                .transactionRef(generateTransactionRef())
                .paidAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // Update booking status
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByBooking(Long bookingId) {

        Payment payment = paymentRepository
                .findByBookingBookingId(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PaymentResponse mapToResponse(Payment payment) {

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .bookingId(payment.getBooking().getBookingId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus().name())
                .transactionRef(payment.getTransactionRef())
                .paidAt(payment.getPaidAt())
                .build();
    }

    private String generateTransactionRef() {

        return "TXN-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}