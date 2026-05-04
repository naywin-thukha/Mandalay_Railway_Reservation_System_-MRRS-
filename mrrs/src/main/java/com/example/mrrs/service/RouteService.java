package com.example.mrrs.service;

import com.example.mrrs.entity.Route;

import java.util.List;

public interface RouteService {

    Route createRoute(Route route);

    List<Route> getAllMandalayRoutes();

    List<Route> searchRoutes(String origin, String destination);

    List<Route> getRoutesFromMandalay();

    List<Route> getRoutesToMandalay();

    Route getRoute(Long id);
}