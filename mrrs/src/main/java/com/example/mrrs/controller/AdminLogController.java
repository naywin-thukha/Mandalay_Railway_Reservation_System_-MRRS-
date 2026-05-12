package com.example.mrrs.controller;


import com.example.mrrs.dto.adminlog.AdminLogRequest;
import com.example.mrrs.dto.adminlog.AdminLogResponse;
import com.example.mrrs.service.AdminLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin-logs")
@RequiredArgsConstructor
public class AdminLogController {

    private final AdminLogService adminLogService;

    @PostMapping
    public void createLog(@RequestBody AdminLogRequest request) {
        adminLogService.log(request);
    }

    @GetMapping
    public List<AdminLogResponse> getAll() {
        return adminLogService.getAllLogs();
    }

    @GetMapping("/admin/{adminId}")
    public List<AdminLogResponse> getByAdmin(@PathVariable Long adminId) {
        return adminLogService.getLogsByAdmin(adminId);
    }

    @GetMapping("/table/{table}")
    public List<AdminLogResponse> getByTable(@PathVariable String table) {
        return adminLogService.getLogsByTable(table);
    }
}
