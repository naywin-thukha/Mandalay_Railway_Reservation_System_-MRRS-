package com.example.mrrs.dto.seat;

import lombok.Data;

@Data
public class SeatRequest {
    private Long trainId;
    private String seatNumber;
    private String seatClass; // VIP / NORMAL
}