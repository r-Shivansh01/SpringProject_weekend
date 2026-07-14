package com.mediflow.notificationservice.dto;

import com.mediflow.notificationservice.entity.NotificationStatus;
import com.mediflow.notificationservice.entity.NotificationType;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;

    private Long patientId;
    private String patientName;

    private NotificationType type;

    private String recipient;

    private String subject;
    private String message;

    private NotificationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    private String failureReason;

    public NotificationResponse() {
    }

    public NotificationResponse(
            Long id,
            Long patientId,
            String patientName,
            NotificationType type,
            String recipient,
            String subject,
            String message,
            NotificationStatus status,
            LocalDateTime createdAt,
            LocalDateTime sentAt,
            String failureReason) {

        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.type = type;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.failureReason = failureReason;
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public NotificationType getType() {
        return type;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
