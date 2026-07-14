package com.mediflow.notificationservice.service;

import com.mediflow.notificationservice.client.PatientClient;
import com.mediflow.notificationservice.dto.NotificationRequest;
import com.mediflow.notificationservice.dto.NotificationResponse;
import com.mediflow.notificationservice.dto.PatientResponse;
import com.mediflow.notificationservice.entity.Notification;
import com.mediflow.notificationservice.entity.NotificationStatus;
import com.mediflow.notificationservice.entity.NotificationType;
import com.mediflow.notificationservice.exception.InvalidNotificationException;
import com.mediflow.notificationservice.exception.ResourceNotFoundException;
import com.mediflow.notificationservice.mapper.NotificationMapper;
import com.mediflow.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final PatientClient patientClient;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            PatientClient patientClient) {

        this.notificationRepository = notificationRepository;
        this.patientClient = patientClient;
    }

    @Override
    public NotificationResponse createNotification(
            NotificationRequest request) {

        PatientResponse patient =
                patientClient.getPatientById(request.getPatientId());

        validateRecipient(request.getType(), patient);

        Notification notification = new Notification();

        notification.setPatientId(request.getPatientId());
        notification.setType(request.getType());
        notification.setSubject(request.getSubject());
        notification.setMessage(request.getMessage());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());

        Notification savedNotification =
                notificationRepository.save(notification);

        simulateSending(savedNotification, patient);

        Notification updatedNotification =
                notificationRepository.save(savedNotification);

        return NotificationMapper.toResponse(
                updatedNotification,
                patient
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(Long id) {

        return buildResponse(findNotification(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByPatientId(
            Long patientId) {

        patientClient.getPatientById(patientId);

        return notificationRepository.findByPatientId(patientId)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByStatus(
            NotificationStatus status) {

        return notificationRepository.findByStatus(status)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByType(
            NotificationType type) {

        return notificationRepository.findByType(type)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getPatientNotificationsByStatus(
            Long patientId,
            NotificationStatus status) {

        patientClient.getPatientById(patientId);

        return notificationRepository
                .findByPatientIdAndStatus(patientId, status)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public NotificationResponse resendNotification(Long id) {

        Notification notification = findNotification(id);

        if (notification.getStatus() == NotificationStatus.SENT) {
            throw new InvalidNotificationException(
                    "A successfully sent notification cannot be resent"
            );
        }

        PatientResponse patient =
                patientClient.getPatientById(
                        notification.getPatientId()
                );

        validateRecipient(notification.getType(), patient);

        notification.setStatus(NotificationStatus.PENDING);
        notification.setSentAt(null);
        notification.setFailureReason(null);

        simulateSending(notification, patient);

        Notification updatedNotification =
                notificationRepository.save(notification);

        return NotificationMapper.toResponse(
                updatedNotification,
                patient
        );
    }

    @Override
    public void deleteNotification(Long id) {

        Notification notification = findNotification(id);

        notificationRepository.delete(notification);
    }

    private Notification findNotification(Long id) {

        return notificationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification not found with id: " + id
                        )
                );
    }

    private NotificationResponse buildResponse(
            Notification notification) {

        PatientResponse patient =
                patientClient.getPatientById(
                        notification.getPatientId()
                );

        return NotificationMapper.toResponse(
                notification,
                patient
        );
    }

    private void validateRecipient(
            NotificationType type,
            PatientResponse patient) {

        if (type == NotificationType.EMAIL
                && isBlank(patient.getEmail())) {

            throw new InvalidNotificationException(
                    "Patient does not have a valid email address"
            );
        }

        if (type == NotificationType.SMS
                && isBlank(patient.getPhone())) {

            throw new InvalidNotificationException(
                    "Patient does not have a valid phone number"
            );
        }
    }

    private void simulateSending(
            Notification notification,
            PatientResponse patient) {

        try {
            String recipient = switch (notification.getType()) {
                case EMAIL -> patient.getEmail();
                case SMS -> patient.getPhone();
                case SYSTEM -> "MediFlow System Inbox";
            };

            System.out.println();
            System.out.println("======================================");
            System.out.println("MEDIFLOW NOTIFICATION");
            System.out.println("Notification ID : " + notification.getId());
            System.out.println("Type            : " + notification.getType());
            System.out.println("Patient         : " + patient.getName());
            System.out.println("Recipient       : " + recipient);
            System.out.println("Subject         : " + notification.getSubject());
            System.out.println("Message         : " + notification.getMessage());
            System.out.println("======================================");
            System.out.println();

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            notification.setFailureReason(null);

        } catch (Exception exception) {

            notification.setStatus(NotificationStatus.FAILED);
            notification.setSentAt(null);
            notification.setFailureReason(exception.getMessage());
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
