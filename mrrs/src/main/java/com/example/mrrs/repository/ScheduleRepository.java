package com.example.mrrs.repository;

import com.example.mrrs.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByRoute_RouteIdAndTravelDate(Long routeId, LocalDate travelDate);

    List<Schedule> findByTrain_TrainId(Long trainId);

    List<Schedule> findByTravelDateBetween(LocalDate start, LocalDate end);
}
