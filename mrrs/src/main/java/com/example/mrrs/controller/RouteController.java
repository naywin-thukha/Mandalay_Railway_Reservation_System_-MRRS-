package com.example.mrrs.controller;

import com.example.mrrs.entity.Route;
import com.example.mrrs.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;


    // ADMIN ONLY - create route
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Route> create(@RequestBody Route route) {
        return ResponseEntity.ok(routeService.createRoute(route));
    }

    //  ADMIN + USER - view Mandalay routes
    @GetMapping("/mandalay")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Route>> getMandalayRoutes() {
        return ResponseEntity.ok(routeService.getAllMandalayRoutes());
    }

    //  USER FEATURE - search routes
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Route>> searchRoutes(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        return ResponseEntity.ok(
                routeService.searchRoutes(origin, destination)
        );
    }
}