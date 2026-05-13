package com.example.mrrs.repository;

import com.example.mrrs.entity.SeatHold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatHoldRepository extends JpaRepository<SeatHold, Long> {

    List<SeatHold> findByExpiresAtBefore(LocalDateTime time);
}