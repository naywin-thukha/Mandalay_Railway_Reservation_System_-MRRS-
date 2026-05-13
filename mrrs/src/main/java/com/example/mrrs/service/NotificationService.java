package com.example.mrrs.service;

import com.example.mrrs.dto.notification.NotificationRequest;
import com.example.mrrs.dto.notification.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse create(NotificationRequest dto);

    List<NotificationResponse> getUserNotifications(Long userId);

    List<NotificationResponse> getUnread(Long userId);

    void markAsRead(Long notificationId);
}