package com.mediflow.billingservice.dto;

import com.mediflow.billingservice.entity.BillStatus;
import jakarta.validation.constraints.NotNull;

public class BillStatusRequest {

    @NotNull(message = "Bill status is required")
    private BillStatus status;

    public BillStatusRequest() {
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }
}
