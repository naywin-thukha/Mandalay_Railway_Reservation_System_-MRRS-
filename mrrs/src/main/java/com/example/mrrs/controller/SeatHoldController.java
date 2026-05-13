package com.example.mrrs.controller;

import com.example.mrrs.dto.seathold.SeatHoldRequest;
import com.example.mrrs.dto.seathold.SeatHoldResponse;
import com.example.mrrs.service.SeatHoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-holds")
@RequiredArgsConstructor
public class SeatHoldController {

    private final SeatHoldService seatHoldService;

    @PostMapping
    public SeatHoldResponse create(@RequestBody SeatHoldRequest dto) {
        return seatHoldService.createHold(dto);
    }

    @GetMapping("/expired")
    public List<SeatHoldResponse> expired() {
        return seatHoldService.getExpiredHolds();
    }
}