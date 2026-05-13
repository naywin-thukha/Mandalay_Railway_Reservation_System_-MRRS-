package com.example.mrrs.service;

import com.example.mrrs.dto.seathold.SeatHoldRequest;
import com.example.mrrs.dto.seathold.SeatHoldResponse;

import java.util.List;

public interface SeatHoldService {

    SeatHoldResponse createHold(SeatHoldRequest dto);

    List<SeatHoldResponse> getExpiredHolds();

    void deleteExpiredHolds();
}