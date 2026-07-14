package com.mediflow.appointmentservice.mapper;

import com.mediflow.appointmentservice.dto.AppointmentRequest;
import com.mediflow.appointmentservice.dto.AppointmentResponse;
import com.mediflow.appointmentservice.dto.DoctorResponse;
import com.mediflow.appointmentservice.dto.PatientResponse;
import com.mediflow.appointmentservice.entity.Appointment;
import com.mediflow.appointmentservice.entity.AppointmentStatus;

public final class AppointmentMapper {

    private AppointmentMapper() {
    }

    public static Appointment toEntity(AppointmentRequest request) {

        Appointment appointment = new Appointment();

        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointment;
    }

    public static AppointmentResponse toResponse(
            Appointment appointment,
            PatientResponse patient,
            DoctorResponse doctor) {

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatientId(),
                patient.getName(),
                appointment.getDoctorId(),
                doctor.getName(),
                doctor.getSpecialization(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getReason(),
                appointment.getStatus()
        );
    }
}
