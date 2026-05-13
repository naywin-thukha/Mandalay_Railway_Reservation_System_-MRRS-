package com.example.mrrs.dto.seathold;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SeatHoldResponse {
    private Long holdId;
    private Long seatId;
    private Long scheduleId;
    private Long userId;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}