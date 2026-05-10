package com.example.mrrs.controller;

import com.example.mrrs.dto.seat.SeatRequest;
import com.example.mrrs.dto.seat.SeatResponse;
import com.example.mrrs.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public SeatResponse createSeat(@RequestBody SeatRequest request) {
        return seatService.createSeat(request);
    }

    @GetMapping("/train/{trainId}")
    public List<SeatResponse> getSeatsByTrain(@PathVariable Long trainId) {
        return seatService.getSeatsByTrain(trainId);
    }

    @GetMapping("/available/{trainId}")
    public List<SeatResponse> getAvailableSeats(@PathVariable Long trainId) {
        return seatService.getAvailableSeats(trainId);
    }

    @PutMapping("/unavailable/{seatId}")
    public void markUnavailable(@PathVariable Long seatId) {
        seatService.markSeatAsUnavailable(seatId);
    }

    @PutMapping("/available/{seatId}")
    public void markAvailable(@PathVariable Long seatId) {
        seatService.markSeatAsAvailable(seatId);
    }
}