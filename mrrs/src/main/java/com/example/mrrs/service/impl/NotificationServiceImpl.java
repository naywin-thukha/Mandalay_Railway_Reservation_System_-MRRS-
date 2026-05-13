package com.example.mrrs.service.impl;

import com.example.mrrs.dto.notification.NotificationRequest;
import com.example.mrrs.dto.notification.NotificationResponse;
import com.example.mrrs.entity.Notification;
import com.example.mrrs.entity.User;
import com.example.mrrs.entity.NotificationType;
import com.example.mrrs.repository.NotificationRepository;
import com.example.mrrs.repository.UserRepository;
import com.example.mrrs.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public NotificationResponse create(NotificationRequest dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .title(dto.getTitle())
                .message(dto.getMessage())
                .type(NotificationType.valueOf(dto.getType()))
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);

        return map(saved);
    }

    @Override
    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationRepository.findByUserUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> getUnread(Long userId) {
        return notificationRepository.findByUserUserIdAndIsReadFalse(userId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        n.setIsRead(true);
        notificationRepository.save(n);
    }

    private NotificationResponse map(Notification n) {
        return NotificationResponse.builder()
                .notificationId(n.getNotificationId())
                .userId(n.getUser().getUserId())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType().name())
                .isRead(n.getIsRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}