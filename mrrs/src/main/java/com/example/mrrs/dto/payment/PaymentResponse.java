package com.example.mrrs.dto.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentResponse {

    private Long paymentId;

    private Long bookingId;

    private String paymentMethod;

    private String paymentStatus;

    private String transactionRef;

    private LocalDateTime paidAt;
}