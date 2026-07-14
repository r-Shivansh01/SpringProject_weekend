package com.mediflow.billingservice.dto;

import com.mediflow.billingservice.entity.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillResponse {

    private Long id;

    private Long appointmentId;

    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;
    private String doctorSpecialization;

    private BigDecimal consultationFee;
    private BigDecimal additionalCharges;
    private BigDecimal totalAmount;

    private BillStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    public BillResponse() {
    }

    public BillResponse(
            Long id,
            Long appointmentId,
            Long patientId,
            String patientName,
            Long doctorId,
            String doctorName,
            String doctorSpecialization,
            BigDecimal consultationFee,
            BigDecimal additionalCharges,
            BigDecimal totalAmount,
            BillStatus status,
            LocalDateTime createdAt,
            LocalDateTime paidAt) {

        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorSpecialization = doctorSpecialization;
        this.consultationFee = consultationFee;
        this.additionalCharges = additionalCharges;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public BigDecimal getAdditionalCharges() {
        return additionalCharges;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BillStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}
