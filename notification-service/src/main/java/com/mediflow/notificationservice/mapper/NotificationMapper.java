package com.mediflow.notificationservice.mapper;

import com.mediflow.notificationservice.dto.NotificationResponse;
import com.mediflow.notificationservice.dto.PatientResponse;
import com.mediflow.notificationservice.entity.Notification;
import com.mediflow.notificationservice.entity.NotificationType;

public final class NotificationMapper {

    private NotificationMapper() {
    }

    public static NotificationResponse toResponse(
            Notification notification,
            PatientResponse patient) {

        String recipient = resolveRecipient(
                notification.getType(),
                patient
        );

        return new NotificationResponse(
                notification.getId(),
                notification.getPatientId(),
                patient.getName(),
                notification.getType(),
                recipient,
                notification.getSubject(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getCreatedAt(),
                notification.getSentAt(),
                notification.getFailureReason()
        );
    }

    private static String resolveRecipient(
            NotificationType type,
            PatientResponse patient) {

        return switch (type) {
            case EMAIL -> patient.getEmail();
            case SMS -> patient.getPhone();
            case SYSTEM -> "MediFlow System Inbox";
        };
    }
}
