package com.example.mrrs.entity;

import com.example.mrrs.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String targetTable;

    private Long targetId;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String ipAddress;

    private LocalDateTime timestamp;
}