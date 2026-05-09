package com.example.mrrs.service;

import com.example.mrrs.dto.schedule.ScheduleRequest;
import com.example.mrrs.dto.schedule.ScheduleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponse createSchedule(ScheduleRequest request);

    List<ScheduleResponse> getAllSchedules();

    List<ScheduleResponse> getSchedulesByDateRange(LocalDate start, LocalDate end);

    List<ScheduleResponse> getSchedulesByRouteAndDate(Long routeId, LocalDate date);

    List<ScheduleResponse> getSchedulesByTrain(Long trainId);

    ScheduleResponse getSchedule(Long id);

    ScheduleResponse updateSchedule(Long id, ScheduleRequest request);

    void deleteSchedule(Long id);
}
