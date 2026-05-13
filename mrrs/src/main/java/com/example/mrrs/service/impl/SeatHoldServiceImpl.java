package com.example.mrrs.service.impl;

import com.example.mrrs.dto.seathold.SeatHoldRequest;
import com.example.mrrs.dto.seathold.SeatHoldResponse;
import com.example.mrrs.entity.*;
import com.example.mrrs.repository.*;
import com.example.mrrs.service.SeatHoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatHoldServiceImpl implements SeatHoldService {

    private final SeatHoldRepository seatHoldRepository;
    private final SeatRepository seatRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    public SeatHoldResponse createHold(SeatHoldRequest dto) {

        Seat seat = seatRepository.findById(dto.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SeatHold hold = SeatHold.builder()
                .seat(seat)
                .schedule(schedule)
                .user(user)
                .expiresAt(dto.getExpiresAt())
                .createdAt(LocalDateTime.now())
                .build();

        SeatHold saved = seatHoldRepository.save(hold);

        return mapToResponse(saved);
    }

    @Override
    public List<SeatHoldResponse> getExpiredHolds() {
        return seatHoldRepository.findByExpiresAtBefore(LocalDateTime.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteExpiredHolds() {
        List<SeatHold> expired = seatHoldRepository.findByExpiresAtBefore(LocalDateTime.now());
        seatHoldRepository.deleteAll(expired);
    }

    private SeatHoldResponse mapToResponse(SeatHold hold) {
        return SeatHoldResponse.builder()
                .holdId(hold.getHoldId())
                .seatId(hold.getSeat().getSeatId())
                .scheduleId(hold.getSchedule().getScheduleId())
                .userId(hold.getUser().getUserId())
                .expiresAt(hold.getExpiresAt())
                .createdAt(hold.getCreatedAt())
                .build();
    }
}