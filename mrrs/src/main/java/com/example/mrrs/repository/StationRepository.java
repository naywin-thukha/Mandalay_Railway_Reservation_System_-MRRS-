package com.example.mrrs.repository;

import com.example.mrrs.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long>{
    Optional<Station> findByStationCode(String stationCode);
}
