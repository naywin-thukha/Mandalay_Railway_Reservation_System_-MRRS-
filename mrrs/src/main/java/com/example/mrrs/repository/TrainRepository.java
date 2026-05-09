package com.example.mrrs.repository;

import com.example.mrrs.entity.Train;
import com.example.mrrs.entity.TrainStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainRepository extends JpaRepository<Train, Long> {

    Optional<Train> findByTrainCode(String trainCode);

    boolean existsByTrainCode(String trainCode);

    List<Train> findByStatus(TrainStatus status);

    List<Train> findByTrainNameContainingIgnoreCase(String trainName);
}