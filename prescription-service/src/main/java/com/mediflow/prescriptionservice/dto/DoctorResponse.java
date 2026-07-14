package com.mediflow.prescriptionservice.dto;

public class DoctorResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private Double consultationFee;
    private Boolean available;

    public DoctorResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
