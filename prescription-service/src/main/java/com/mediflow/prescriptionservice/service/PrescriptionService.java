package com.mediflow.prescriptionservice.service;

import com.mediflow.prescriptionservice.dto.PrescriptionRequest;
import com.mediflow.prescriptionservice.dto.PrescriptionResponse;

import java.util.List;

public interface PrescriptionService {

    PrescriptionResponse createPrescription(PrescriptionRequest request);

    List<PrescriptionResponse> getAllPrescriptions();

    PrescriptionResponse getPrescriptionById(Long id);

    List<PrescriptionResponse> getPrescriptionsByPatientId(Long patientId);

    List<PrescriptionResponse> getPrescriptionsByDoctorId(Long doctorId);

    List<PrescriptionResponse> getPrescriptionsByAppointmentId(
            Long appointmentId
    );

    void deletePrescription(Long id);
}
