package com.example.mrrs.mapper;

import com.example.mrrs.dto.schedule.ScheduleResponse;
import com.example.mrrs.entity.Schedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public ScheduleResponse toResponse(Schedule schedule){
        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .trainName(schedule.getTrain().getTrainName())
                .trainCode(schedule.getTrain().getTrainCode())
                .originStation(schedule.getRoute().getOrigin().getStationName())
                .destinationStation(schedule.getRoute().getDestination().getStationName())
                .travelDate(schedule.getTravelDate())
                .departureTime(schedule.getDepartureTime())
                .arrivalTime(schedule.getArrivalTime())
                .price(schedule.getPrice())
                .build();
    }
}
