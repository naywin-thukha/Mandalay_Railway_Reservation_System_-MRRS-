package com.example.mrrs.repository;

import com.example.mrrs.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByOrigin_StationCodeAndDestination_StationCode(String origin, String destination);

    List<Route> findByOrigin_StationCode(String origin);

    List<Route> findByDestination_StationCode(String destination);

    List<Route> findByOrigin_StationCodeOrDestination_StationCode(String origin, String destination);
}