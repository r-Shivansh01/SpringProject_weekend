package com.mediflow.billingservice.dto;

public class DoctorResponse {

    private Long id;
    private String name;
    private String specialization;
    private Double consultationFee;
    private Boolean available;

    public DoctorResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public Boolean getAvailable() {
        return available;
    }
}
