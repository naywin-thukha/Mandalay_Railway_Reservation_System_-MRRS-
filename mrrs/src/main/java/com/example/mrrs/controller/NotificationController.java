package com.example.mrrs.controller;

import com.example.mrrs.dto.notification.NotificationRequest;
import com.example.mrrs.dto.notification.NotificationResponse;
import com.example.mrrs.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponse create(@RequestBody NotificationRequest dto) {
        return notificationService.create(dto);
    }

    @GetMapping("/{userId}")
    public List<NotificationResponse> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/{userId}/unread")
    public List<NotificationResponse> getUnread(@PathVariable Long userId) {
        return notificationService.getUnread(userId);
    }

    @PutMapping("/read/{id}")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}