package com.example.mrrs.dto.train;

import com.example.mrrs.entity.TrainStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainResponse {

    private Long trainId;
    private String trainName;
    private String trainCode;
    private Integer totalSeats;
    private TrainStatus status;
}