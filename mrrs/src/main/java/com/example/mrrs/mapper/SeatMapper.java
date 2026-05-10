package com.example.mrrs.mapper;

import com.example.mrrs.dto.seat.SeatResponse;
import com.example.mrrs.entity.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public SeatResponse toResponse(Seat seat) {
        return SeatResponse.builder()
                .seatId(seat.getSeatId())
                .trainId(seat.getTrain().getTrainId())
                .seatNumber(seat.getSeatNumber())
                .seatClass(seat.getSeatClass().name())
                .isAvailable(seat.getIsAvailable())
                .build();
    }
}