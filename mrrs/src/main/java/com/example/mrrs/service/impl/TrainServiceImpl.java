package com.example.mrrs.service.impl;

import com.example.mrrs.dto.train.TrainRequest;
import com.example.mrrs.dto.train.TrainResponse;
import com.example.mrrs.entity.Train;
import com.example.mrrs.repository.TrainRepository;
import com.example.mrrs.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    @Override
    public TrainResponse createTrain(TrainRequest request) {

        if (trainRepository.existsByTrainCode(request.getTrainCode())) {
            throw new RuntimeException("Train code already exists");
        }

        Train train = Train.builder()
                .trainName(request.getTrainName())
                .trainCode(request.getTrainCode())
                .totalSeats(request.getTotalSeats())
                .status(request.getStatus() != null ? request.getStatus() : null)
                .build();

        return mapToResponse(trainRepository.save(train));
    }

    @Override
    public TrainResponse getTrainById(Long id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        return mapToResponse(train);
    }

    @Override
    public List<TrainResponse> getAllTrains() {
        return trainRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TrainResponse updateTrain(Long id, TrainRequest request) {

        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        train.setTrainName(request.getTrainName());
        train.setTrainCode(request.getTrainCode());
        train.setTotalSeats(request.getTotalSeats());
        train.setStatus(request.getStatus());

        return mapToResponse(trainRepository.save(train));
    }

    @Override
    public void deleteTrain(Long id) {
        trainRepository.deleteById(id);
    }

    private TrainResponse mapToResponse(Train train) {
        return TrainResponse.builder()
                .trainId(train.getTrainId())
                .trainName(train.getTrainName())
                .trainCode(train.getTrainCode())
                .totalSeats(train.getTotalSeats())
                .status(train.getStatus())
                .build();
    }
}