package com.example.mrrs.dto.route;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequestDTO {

    private Long originStationId;
    private Long destinationStationId;

    private Double distanceKm;
    private Integer durationMinutes;
}