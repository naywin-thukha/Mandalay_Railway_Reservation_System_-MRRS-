package com.example.mrrs.dto.seathold;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeatHoldRequest {
    private Long seatId;
    private Long scheduleId;
    private Long userId;
    private LocalDateTime expiresAt;
}