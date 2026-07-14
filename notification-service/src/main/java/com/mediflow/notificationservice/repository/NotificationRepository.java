package com.mediflow.notificationservice.repository;

import com.mediflow.notificationservice.entity.Notification;
import com.mediflow.notificationservice.entity.NotificationStatus;
import com.mediflow.notificationservice.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByPatientId(Long patientId);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByType(NotificationType type);

    List<Notification> findByPatientIdAndStatus(
            Long patientId,
            NotificationStatus status
    );
}
