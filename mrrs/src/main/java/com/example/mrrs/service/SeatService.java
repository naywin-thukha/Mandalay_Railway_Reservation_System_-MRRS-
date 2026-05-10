package com.example.mrrs.service;

import com.example.mrrs.dto.seat.SeatRequest;
import com.example.mrrs.dto.seat.SeatResponse;

import java.util.List;

public interface SeatService {

    SeatResponse createSeat(SeatRequest request);

    List<SeatResponse> getSeatsByTrain(Long trainId);

    List<SeatResponse> getAvailableSeats(Long trainId);

    void markSeatAsUnavailable(Long seatId);

    void markSeatAsAvailable(Long seatId);
}