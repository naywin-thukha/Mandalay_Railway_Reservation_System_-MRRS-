package com.example.mrrs.service.impl;

import com.example.mrrs.dto.schedule.ScheduleRequest;
import com.example.mrrs.dto.schedule.ScheduleResponse;
import com.example.mrrs.entity.Route;
import com.example.mrrs.entity.Schedule;
import com.example.mrrs.entity.Train;
import com.example.mrrs.mapper.ScheduleMapper;
import com.example.mrrs.repository.RouteRepository;
import com.example.mrrs.repository.ScheduleRepository;
import com.example.mrrs.repository.TrainRepository;
import com.example.mrrs.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest request) {

        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));

        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        Schedule schedule = Schedule.builder()
                .train(train)
                .route(route)
                .travelDate(request.getTravelDate())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .price(request.getPrice())
                .build();

        return scheduleMapper.toResponse(scheduleRepository.save(schedule));
    }

    @Override
    public List<ScheduleResponse> getSchedulesByRouteAndDate(Long routeId, LocalDate date) {
        return scheduleRepository
                .findByRoute_RouteIdAndTravelDate(routeId, date)
                .stream()
                .map(scheduleMapper::toResponse)
                .toList();
    }

    @Override
    public List<ScheduleResponse> getSchedulesByTrain(Long trainId) {
        return scheduleRepository
                .findByTrain_TrainId(trainId)
                .stream()
                .map(scheduleMapper::toResponse)
                .toList();
    }

    @Override
    public ScheduleResponse getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        return scheduleMapper.toResponse(schedule);
    }
}

