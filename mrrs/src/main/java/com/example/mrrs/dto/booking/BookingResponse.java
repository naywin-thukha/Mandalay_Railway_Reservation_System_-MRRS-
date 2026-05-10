package com.example.mrrs.dto.booking;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingResponse {

    private Long bookingId;

    private String passengerName;

    private String trainName;

    private String route;

    private LocalDateTime bookingDate;

    private List<String> seats;

    private BigDecimal totalAmount;

    private String status;
}