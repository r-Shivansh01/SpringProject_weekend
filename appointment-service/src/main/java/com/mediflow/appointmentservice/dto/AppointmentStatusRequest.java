package com.mediflow.appointmentservice.dto;

import com.mediflow.appointmentservice.entity.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public class AppointmentStatusRequest {

    @NotNull(message = "Appointment status is required")
    private AppointmentStatus status;

    public AppointmentStatusRequest() {
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
