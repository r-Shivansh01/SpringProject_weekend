package com.mediflow.appointmentservice.service;

import com.mediflow.appointmentservice.client.DoctorClient;
import com.mediflow.appointmentservice.client.PatientClient;
import com.mediflow.appointmentservice.dto.*;
import com.mediflow.appointmentservice.entity.Appointment;
import com.mediflow.appointmentservice.entity.AppointmentStatus;
import com.mediflow.appointmentservice.exception.InvalidAppointmentException;
import com.mediflow.appointmentservice.exception.ResourceNotFoundException;
import com.mediflow.appointmentservice.mapper.AppointmentMapper;
import com.mediflow.appointmentservice.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            PatientClient patientClient,
            DoctorClient doctorClient) {

        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    @Override
    public AppointmentResponse createAppointment(
            AppointmentRequest request) {

        PatientResponse patient =
                patientClient.getPatientById(request.getPatientId());

        DoctorResponse doctor =
                doctorClient.getDoctorById(request.getDoctorId());

        if (!Boolean.TRUE.equals(doctor.getAvailable())) {
            throw new InvalidAppointmentException(
                    "Doctor is currently unavailable"
            );
        }

        validateAppointmentDateTime(request);

        boolean alreadyBooked =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
                                request.getDoctorId(),
                                request.getAppointmentDate(),
                                request.getAppointmentTime(),
                                AppointmentStatus.CANCELLED
                        );

        if (alreadyBooked) {
            throw new InvalidAppointmentException(
                    "Doctor already has an appointment at the selected date and time"
            );
        }

        Appointment appointment =
                AppointmentMapper.toEntity(request);

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        return AppointmentMapper.toResponse(
                savedAppointment,
                patient,
                doctor
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {

        return buildResponse(findAppointment(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatientId(
            Long patientId) {

        patientClient.getPatientById(patientId);

        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDoctorId(
            Long doctorId) {

        doctorClient.getDoctorById(doctorId);

        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByStatus(
            AppointmentStatus status) {

        return appointmentRepository.findByStatus(status)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public AppointmentResponse updateAppointmentStatus(
            Long id,
            AppointmentStatusRequest request) {

        Appointment appointment = findAppointment(id);

        appointment.setStatus(request.getStatus());

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        return buildResponse(updatedAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {

        Appointment appointment = findAppointment(id);

        appointmentRepository.delete(appointment);
    }

    private Appointment findAppointment(Long id) {

        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id
                        )
                );
    }

    private AppointmentResponse buildResponse(
            Appointment appointment) {

        PatientResponse patient =
                patientClient.getPatientById(
                        appointment.getPatientId()
                );

        DoctorResponse doctor =
                doctorClient.getDoctorById(
                        appointment.getDoctorId()
                );

        return AppointmentMapper.toResponse(
                appointment,
                patient,
                doctor
        );
    }

    private void validateAppointmentDateTime(
            AppointmentRequest request) {

        LocalDateTime requestedDateTime =
                LocalDateTime.of(
                        request.getAppointmentDate(),
                        request.getAppointmentTime()
                );

        if (requestedDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentException(
                    "Appointment date and time must be in the future"
            );
        }
    }
}
