package com.mediflow.prescriptionservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponse {

    private Long id;

    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;
    private String doctorSpecialization;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String reason;
    private String status;

    public AppointmentResponse() {
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getStatus() {
        return status;
    }
}
