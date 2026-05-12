package com.example.mrrs.dto.ticket;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponse {

    private Long ticketId;

    private String pnrNumber;

    private Long bookingId;

    private String passengerName;

    private String trainName;

    private String sourceStation;

    private String destinationStation;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private String seatNumbers;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime issuedAt;
}
