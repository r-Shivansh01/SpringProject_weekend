package com.mediflow.billingservice.mapper;

import com.mediflow.billingservice.dto.BillResponse;
import com.mediflow.billingservice.dto.DoctorResponse;
import com.mediflow.billingservice.dto.PatientResponse;
import com.mediflow.billingservice.entity.Bill;

public final class BillMapper {

    private BillMapper() {
    }

    public static BillResponse toResponse(
            Bill bill,
            PatientResponse patient,
            DoctorResponse doctor) {

        return new BillResponse(
                bill.getId(),
                bill.getAppointmentId(),
                bill.getPatientId(),
                patient.getName(),
                bill.getDoctorId(),
                doctor.getName(),
                doctor.getSpecialization(),
                bill.getConsultationFee(),
                bill.getAdditionalCharges(),
                bill.getTotalAmount(),
                bill.getStatus(),
                bill.getCreatedAt(),
                bill.getPaidAt()
        );
    }
}
