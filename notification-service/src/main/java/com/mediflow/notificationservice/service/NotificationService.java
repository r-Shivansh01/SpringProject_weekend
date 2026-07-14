package com.mediflow.notificationservice.service;

import com.mediflow.notificationservice.dto.NotificationRequest;
import com.mediflow.notificationservice.dto.NotificationResponse;
import com.mediflow.notificationservice.entity.NotificationStatus;
import com.mediflow.notificationservice.entity.NotificationType;

import java.util.List;

public interface NotificationService {

    NotificationResponse createNotification(NotificationRequest request);

    List<NotificationResponse> getAllNotifications();

    NotificationResponse getNotificationById(Long id);

    List<NotificationResponse> getNotificationsByPatientId(Long patientId);

    List<NotificationResponse> getNotificationsByStatus(
            NotificationStatus status
    );

    List<NotificationResponse> getNotificationsByType(
            NotificationType type
    );

    List<NotificationResponse> getPatientNotificationsByStatus(
            Long patientId,
            NotificationStatus status
    );

    NotificationResponse resendNotification(Long id);

    void deleteNotification(Long id);
}
