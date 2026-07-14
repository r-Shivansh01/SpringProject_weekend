package com.mediflow.billingservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class BillRequest {

    @NotNull(message = "Appointment id is required")
    private Long appointmentId;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Additional charges cannot be negative"
    )
    private BigDecimal additionalCharges;

    public BillRequest() {
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigDecimal getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(BigDecimal additionalCharges) {
        this.additionalCharges = additionalCharges;
    }
}
