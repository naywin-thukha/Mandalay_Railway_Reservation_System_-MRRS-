package com.example.mrrs.dto.adminlog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLogRequest {

    private Long adminId;

    private String action;

    private String targetTable;

    private Long targetId;

    private String description;

    private String ipAddress;
}