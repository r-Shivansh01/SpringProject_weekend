package com.mediflow.doctorservice.dto;

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


    public DoctorResponse(
            Long id,
            String name,
            String email,
            String phone,
            String specialization,
            Double consultationFee,
            Boolean available) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.available = available;
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


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getSpecialization() {
        return specialization;
    }


    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public Double getConsultationFee() {
        return consultationFee;
    }


    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }


    public Boolean getAvailable() {
        return available;
    }


    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
