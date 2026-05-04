package com.example.mrrs.dto.train;

import com.example.mrrs.entity.TrainStatus;
import lombok.Data;

@Data
public class TrainRequest {

    private String trainName;
    private String trainCode;
    private Integer totalSeats;
    private TrainStatus status;
}
