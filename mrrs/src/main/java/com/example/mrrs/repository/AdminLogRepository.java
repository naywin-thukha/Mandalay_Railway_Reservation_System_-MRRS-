package com.example.mrrs.repository;

import com.example.mrrs.entity.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminLogRepository extends JpaRepository<AdminLog, Long> {

    List<AdminLog> findByAdmin_UserId(Long adminId);

    List<AdminLog> findByTargetTable(String targetTable);

    List<AdminLog> findByAction(String action);
}