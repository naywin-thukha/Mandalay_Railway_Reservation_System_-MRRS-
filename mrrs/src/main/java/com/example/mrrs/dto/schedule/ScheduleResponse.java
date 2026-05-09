package com.example.mrrs.dto.schedule;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ScheduleResponse {

    private Long scheduleId;

    private String trainName;
    private String trainCode;

    private String originStation;
    private String destinationStation;

    private LocalDate travelDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private BigDecimal price;
}
