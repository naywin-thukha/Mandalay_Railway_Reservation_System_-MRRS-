package com.example.mrrs.service;

import com.example.mrrs.entity.Station;

import java.util.List;

public interface StationService {

    Station createStation(Station station);

    List<Station> getAllStations();
}