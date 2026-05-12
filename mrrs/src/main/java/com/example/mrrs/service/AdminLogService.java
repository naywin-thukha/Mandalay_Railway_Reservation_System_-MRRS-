package com.example.mrrs.service;


import com.example.mrrs.dto.adminlog.AdminLogRequest;
import com.example.mrrs.dto.adminlog.AdminLogResponse;

import java.util.List;

public interface AdminLogService {

    void log(AdminLogRequest request);

    List<AdminLogResponse> getAllLogs();

    List<AdminLogResponse> getLogsByAdmin(Long adminId);

    List<AdminLogResponse> getLogsByTable(String table);
}
