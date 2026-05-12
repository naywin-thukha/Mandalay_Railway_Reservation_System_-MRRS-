package com.example.mrrs.service.impl;

import com.example.mrrs.dto.adminlog.AdminLogRequest;
import com.example.mrrs.dto.adminlog.AdminLogResponse;
import com.example.mrrs.entity.AdminLog;
import com.example.mrrs.entity.User;
import com.example.mrrs.mapper.AdminLogMapper;
import com.example.mrrs.repository.AdminLogRepository;
import com.example.mrrs.repository.UserRepository;
import com.example.mrrs.service.AdminLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminLogServiceImpl implements AdminLogService {

    private final AdminLogRepository adminLogRepository;
    private final UserRepository userRepository;

    @Override
    public void log(AdminLogRequest request) {

        User admin = userRepository.findById(request.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        AdminLog log = AdminLog.builder()
                .admin(admin)
                .action(request.getAction())
                .targetTable(request.getTargetTable())
                .targetId(request.getTargetId())
                .description(request.getDescription())
                .ipAddress(request.getIpAddress())
                .timestamp(LocalDateTime.now())
                .build();

        adminLogRepository.save(log);
    }

    @Override
    public List<AdminLogResponse> getAllLogs() {
        return adminLogRepository.findAll()
                .stream()
                .map(AdminLogMapper::toResponse)
                .toList();
    }

    @Override
    public List<AdminLogResponse> getLogsByAdmin(Long adminId) {
        return adminLogRepository.findByAdmin_UserId(adminId)
                .stream()
                .map(AdminLogMapper::toResponse)
                .toList();
    }

    @Override
    public List<AdminLogResponse> getLogsByTable(String table) {
        return adminLogRepository.findByTargetTable(table)
                .stream()
                .map(AdminLogMapper::toResponse)
                .toList();
    }
}