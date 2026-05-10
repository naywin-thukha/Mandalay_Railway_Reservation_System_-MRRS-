package com.example.mrrs.dto.booking;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {

    private Long userId;

    private Long scheduleId;

    private List<Long> seatIds;
}