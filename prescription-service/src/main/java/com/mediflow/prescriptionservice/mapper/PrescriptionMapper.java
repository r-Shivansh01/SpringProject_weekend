package com.mediflow.prescriptionservice.mapper;

import com.mediflow.prescriptionservice.dto.DoctorResponse;
import com.mediflow.prescriptionservice.dto.PatientResponse;
import com.mediflow.prescriptionservice.dto.PrescriptionRequest;
import com.mediflow.prescriptionservice.dto.PrescriptionResponse;
import com.mediflow.prescriptionservice.entity.Prescription;

import java.time.LocalDate;

public final class PrescriptionMapper {

    private PrescriptionMapper() {
    }

    public static Prescription toEntity(PrescriptionRequest request) {

        Prescription prescription = new Prescription();

        prescription.setAppointmentId(request.getAppointmentId());
        prescription.setPatientId(request.getPatientId());
        prescription.setDoctorId(request.getDoctorId());
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setMedicines(request.getMedicines());
        prescription.setDosage(request.getDosage());
        prescription.setInstructions(request.getInstructions());
        prescription.setPrescriptionDate(LocalDate.now());

        return prescription;
    }

    public static PrescriptionResponse toResponse(
            Prescription prescription,
            PatientResponse patient,
            DoctorResponse doctor) {

        return new PrescriptionResponse(
                prescription.getId(),
                prescription.getAppointmentId(),
                prescription.getPatientId(),
                patient.getName(),
                prescription.getDoctorId(),
                doctor.getName(),
                doctor.getSpecialization(),
                prescription.getDiagnosis(),
                prescription.getMedicines(),
                prescription.getDosage(),
                prescription.getInstructions(),
                prescription.getPrescriptionDate()
        );
    }
}
