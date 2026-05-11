package com.example.mrrs.service;

import com.example.mrrs.dto.payment.PaymentRequest;
import com.example.mrrs.dto.payment.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest request);

    PaymentResponse getPaymentByBooking(Long bookingId);

    List<PaymentResponse> getAllPayments();
}