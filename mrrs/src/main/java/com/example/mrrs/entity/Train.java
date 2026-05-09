package com.example.mrrs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trains")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainId;

    @Column(nullable = false)
    private String trainName;

    @Column(nullable = false, unique = true)
    private String trainCode;

    @Column(nullable = false)
    private Integer totalSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TrainStatus status = TrainStatus.ACTIVE;
}