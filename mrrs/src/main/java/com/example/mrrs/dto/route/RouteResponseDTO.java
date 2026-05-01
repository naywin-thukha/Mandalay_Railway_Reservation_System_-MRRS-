package com.example.mrrs.dto.route;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponseDTO {

    private Long routeId;

    private Long originStationId;
    private String originStationName;

    private Long destinationStationId;
    private String destinationStationName;

    private Double distanceKm;
    private Integer durationMinutes;
}