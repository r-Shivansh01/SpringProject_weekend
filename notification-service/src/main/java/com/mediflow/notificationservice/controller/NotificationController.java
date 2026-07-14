package com.mediflow.notificationservice.controller;

import com.mediflow.notificationservice.dto.NotificationRequest;
import com.mediflow.notificationservice.dto.NotificationResponse;
import com.mediflow.notificationservice.entity.NotificationStatus;
import com.mediflow.notificationservice.entity.NotificationType;
import com.mediflow.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody NotificationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        notificationService.createNotification(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>>
    getAllNotifications() {

        return ResponseEntity.ok(
                notificationService.getAllNotifications()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse>
    getNotificationById(@PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.getNotificationById(id)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<NotificationResponse>>
    getNotificationsByPatientId(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                notificationService
                        .getNotificationsByPatientId(patientId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponse>>
    getNotificationsByStatus(
            @PathVariable NotificationStatus status) {

        return ResponseEntity.ok(
                notificationService
                        .getNotificationsByStatus(status)
        );
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationResponse>>
    getNotificationsByType(
            @PathVariable NotificationType type) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByType(type)
        );
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    public ResponseEntity<List<NotificationResponse>>
    getPatientNotificationsByStatus(
            @PathVariable Long patientId,
            @PathVariable NotificationStatus status) {

        return ResponseEntity.ok(
                notificationService
                        .getPatientNotificationsByStatus(
                                patientId,
                                status
                        )
        );
    }

    @PutMapping("/{id}/resend")
    public ResponseEntity<NotificationResponse>
    resendNotification(@PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.resendNotification(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.noContent().build();
    }
}
