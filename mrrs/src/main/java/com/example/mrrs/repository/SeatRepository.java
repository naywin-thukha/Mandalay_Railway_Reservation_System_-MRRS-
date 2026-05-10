package com.example.mrrs.repository;

import com.example.mrrs.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByTrain_TrainId(Long trainId);

    List<Seat> findByTrain_TrainIdAndIsAvailableTrue(Long trainId);
}