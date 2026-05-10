package com.example.mrrs.dto.seat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatResponse {
    private Long seatId;
    private Long trainId;
    private String seatNumber;
    private String seatClass;
    private Boolean isAvailable;
}