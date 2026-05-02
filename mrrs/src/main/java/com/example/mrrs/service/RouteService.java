package com.example.mrrs.service;

import com.example.mrrs.entity.Route;
import com.example.mrrs.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RouteService {

    private final RouteRepository routeRepository;
    private static final static String MDY = "MDY";

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route createRoute(Route route) {
        String originCode = route.getOrigin().getStationCode();
        String destCode = route.getDestination().getStationCode();

        if (!originCode.equals("MDY") && !destCode.equals("MDY")){
            throw new IllegalArgumentException("Route must involve Mandalay Station (MDY)");
        }
        return routeRepository.save(route);
    }

    public List<Route> getAllMandalayRoutes() {
        return routeRepository.findByOrigin_StationCodeOrDestination_StationCode(MDY, MDY);
    }

    public List<Route> searchRoutes(String origin, String destination) {
        return routeRepository
                .findByOrigin_StationCodeAndDestination_StationCode(origin, destination);
    }
    public List<Route> getRoutesFromMandalay() {
        return routeRepository.findByOrigin_StationCode(MDY);
    }

    public List<Route> getRoutesToMandalay() {
        return routeRepository.findByDestination_StationCode(MDY);
    }

    public Route getRoute(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }
}
