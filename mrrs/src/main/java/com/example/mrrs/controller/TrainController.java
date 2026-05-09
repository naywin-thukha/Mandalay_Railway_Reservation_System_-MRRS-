package com.example.mrrs.controller;

import com.example.mrrs.dto.train.TrainRequest;
import com.example.mrrs.dto.train.TrainResponse;
import com.example.mrrs.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @PostMapping
    public ResponseEntity<TrainResponse> create(@RequestBody TrainRequest request) {
        return ResponseEntity.ok(trainService.createTrain(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(trainService.getTrainById(id));
    }

    @GetMapping
    public ResponseEntity<List<TrainResponse>> getAll() {
        return ResponseEntity.ok(trainService.getAllTrains());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainResponse> update(
            @PathVariable Long id,
            @RequestBody TrainRequest request) {
        return ResponseEntity.ok(trainService.updateTrain(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        trainService.deleteTrain(id);
        return ResponseEntity.ok("Train deleted successfully");
    }
}