package com.example.mrrs.scheduler;

import com.example.mrrs.service.SeatHoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatHoldScheduler {

    private final SeatHoldService seatHoldService;

    // runs every 1 minute
    @Scheduled(fixedRate = 60000)
    public void cleanExpiredHolds() {
        seatHoldService.deleteExpiredHolds();
    }
}