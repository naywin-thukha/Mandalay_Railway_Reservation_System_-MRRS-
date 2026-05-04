package com.example.mrrs.service;

import com.example.mrrs.dto.train.TrainRequest;
import com.example.mrrs.dto.train.TrainResponse;

import java.util.List;

public interface TrainService {

    TrainResponse createTrain(TrainRequest request);

    TrainResponse getTrainById(Long id);

    List<TrainResponse> getAllTrains();

    TrainResponse updateTrain(Long id, TrainRequest request);

    void deleteTrain(Long id);
}