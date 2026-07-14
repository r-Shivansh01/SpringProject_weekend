package com.mediflow.doctorservice.dto;

import jakarta.validation.constraints.*;

public class DoctorRequest {

    @NotBlank(message = "Doctor name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Doctor name must contain 2 to 100 characters"
    )
    private String name;


    @NotBlank(message = "Doctor email is required")
    @Email(message = "Enter a valid email address")
    private String email;


    @NotBlank(message = "Doctor phone number is required")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    private String phone;


    @NotBlank(message = "Specialization is required")
    @Size(
            min = 2,
            max = 100,
            message = "Specialization must contain 2 to 100 characters"
    )
    private String specialization;


    @NotNull(message = "Consultation fee is required")
    @PositiveOrZero(
            message = "Consultation fee cannot be negative"
    )
    private Double consultationFee;


    @NotNull(message = "Availability is required")
    private Boolean available;


    public DoctorRequest() {
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
