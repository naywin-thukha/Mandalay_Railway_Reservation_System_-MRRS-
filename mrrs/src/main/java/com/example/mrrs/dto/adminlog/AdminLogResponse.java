package com.example.mrrs.dto.adminlog;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AdminLogResponse {

    private Long logId;

    private Long adminId;

    private String action;

    private String targetTable;

    private Long targetId;

    private String description;

    private String ipAddress;

    private LocalDateTime timestamp;
}