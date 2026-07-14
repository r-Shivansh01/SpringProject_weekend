package com.mediflow.appointmentservice.service;

import com.mediflow.appointmentservice.dto.AppointmentRequest;
import com.mediflow.appointmentservice.dto.AppointmentResponse;
import com.mediflow.appointmentservice.dto.AppointmentStatusRequest;
import com.mediflow.appointmentservice.entity.AppointmentStatus;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest request);

    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse getAppointmentById(Long id);

    List<AppointmentResponse> getAppointmentsByPatientId(Long patientId);

    List<AppointmentResponse> getAppointmentsByDoctorId(Long doctorId);

    List<AppointmentResponse> getAppointmentsByStatus(AppointmentStatus status);

    AppointmentResponse updateAppointmentStatus(
            Long id,
            AppointmentStatusRequest request
    );

    void deleteAppointment(Long id);
}
