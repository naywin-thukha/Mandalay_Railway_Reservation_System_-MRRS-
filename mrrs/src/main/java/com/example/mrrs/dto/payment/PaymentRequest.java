package com.example.mrrs.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    private Long bookingId;

    private String paymentMethod;
}