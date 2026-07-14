package com.mediflow.billingservice.dto;

public class AppointmentResponse {

    private Long id;
    private Long patientId;
    private Long doctorId;
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
