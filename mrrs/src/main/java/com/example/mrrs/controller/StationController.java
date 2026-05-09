package com.example.mrrs.controller;

import com.example.mrrs.entity.Station;
import com.example.mrrs.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    //  ADMIN ONLY - create station
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Station> create(@RequestBody Station station) {
        return ResponseEntity.ok(stationService.createStation(station));
    }

    //  ADMIN + USER - view stations
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Station>> getAll() {
        return ResponseEntity.ok(stationService.getAllStations());
    }
}