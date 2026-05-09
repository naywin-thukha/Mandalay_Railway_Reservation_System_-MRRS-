package com.example.mrrs.dto.station;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationResponseDTO {

    private Long stationId;
    private String stationName;
    private String stationCode;
}