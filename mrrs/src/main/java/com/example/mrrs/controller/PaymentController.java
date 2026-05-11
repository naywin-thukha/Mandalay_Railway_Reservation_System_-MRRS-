package com.example.mrrs.controller;

import com.example.mrrs.dto.payment.PaymentRequest;
import com.example.mrrs.dto.payment.PaymentResponse;
import com.example.mrrs.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse makePayment(
            @RequestBody PaymentRequest request) {

        return paymentService.makePayment(request);
    }

    @GetMapping("/booking/{bookingId}")
    public PaymentResponse getPaymentByBooking(
            @PathVariable Long bookingId) {

        return paymentService.getPaymentByBooking(bookingId);
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {

        return paymentService.getAllPayments();
    }
}