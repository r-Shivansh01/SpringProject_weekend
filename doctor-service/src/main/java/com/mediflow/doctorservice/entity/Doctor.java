package com.mediflow.doctorservice.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "doctors",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_email",
                        columnNames = "email"
                )
        }
)
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String phone;


    @Column(nullable = false)
    private String specialization;


    @Column(nullable = false)
    private Double consultationFee;


    @Column(nullable = false)
    private Boolean available;


    public Doctor() {
    }


    public Doctor(
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
