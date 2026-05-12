package com.example.mrrs.mapper;



import com.example.mrrs.dto.adminlog.AdminLogResponse;
import com.example.mrrs.entity.AdminLog;

public class AdminLogMapper {

    public static AdminLogResponse toResponse(AdminLog log) {
        return AdminLogResponse.builder()
                .logId(log.getLogId())
                .adminId(log.getAdmin().getUserId())
                .action(log.getAction())
                .targetTable(log.getTargetTable())
                .targetId(log.getTargetId())
                .description(log.getDescription())
                .ipAddress(log.getIpAddress())
                .timestamp(log.getTimestamp())
                .build();
    }
}
