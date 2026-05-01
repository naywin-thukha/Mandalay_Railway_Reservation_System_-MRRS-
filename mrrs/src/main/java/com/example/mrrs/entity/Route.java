package com.example.mrrs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "origin_station_id", nullable = false)
    private Station origin;

    @ManyToOne
    @JoinColumn(name = "destination_station_id", nullable = false)
    private Station destination;

    private Double distanceKm;
    private Integer durationMinutes;

}
