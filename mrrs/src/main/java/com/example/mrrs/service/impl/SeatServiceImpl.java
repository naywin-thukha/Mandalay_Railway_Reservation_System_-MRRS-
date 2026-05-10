package com.example.mrrs.service.impl;

import com.example.mrrs.dto.seat.SeatRequest;
import com.example.mrrs.dto.seat.SeatResponse;
import com.example.mrrs.entity.Seat;
import com.example.mrrs.entity.Train;
import com.example.mrrs.mapper.SeatMapper;
import com.example.mrrs.repository.SeatRepository;
import com.example.mrrs.repository.TrainRepository;
import com.example.mrrs.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final TrainRepository trainRepository;
    private final SeatMapper seatMapper;

    @Override
    public SeatResponse createSeat(SeatRequest request) {

        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new RuntimeException("Train not found"));

        Seat seat = Seat.builder()
                .train(train)
                .seatNumber(request.getSeatNumber())
                .seatClass(Seat.SeatClass.valueOf(request.getSeatClass()))
                .isAvailable(true)
                .build();

        return seatMapper.toResponse(seatRepository.save(seat));
    }

    @Override
    public List<SeatResponse> getSeatsByTrain(Long trainId) {
        return seatRepository.findByTrain_TrainId(trainId)
                .stream()
                .map(seatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatResponse> getAvailableSeats(Long trainId) {
        return seatRepository.findByTrain_TrainIdAndIsAvailableTrue(trainId)
                .stream()
                .map(seatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void markSeatAsUnavailable(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        seat.setIsAvailable(false);
        seatRepository.save(seat);
    }

    @Override
    public void markSeatAsAvailable(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        seat.setIsAvailable(true);
        seatRepository.save(seat);
    }
}