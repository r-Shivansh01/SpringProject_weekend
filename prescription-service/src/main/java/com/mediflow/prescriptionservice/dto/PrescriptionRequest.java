package com.mediflow.prescriptionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PrescriptionRequest {

    @NotNull(message = "Appointment id is required")
    private Long appointmentId;

    @NotNull(message = "Patient id is required")
    private Long patientId;

    @NotNull(message = "Doctor id is required")
    private Long doctorId;

    @NotBlank(message = "Diagnosis is required")
    @Size(max = 500, message = "Diagnosis cannot exceed 500 characters")
    private String diagnosis;

    @NotBlank(message = "Medicines are required")
    @Size(max = 1000, message = "Medicines cannot exceed 1000 characters")
    private String medicines;

    @NotBlank(message = "Dosage is required")
    @Size(max = 500, message = "Dosage cannot exceed 500 characters")
    private String dosage;

    @NotBlank(message = "Instructions are required")
    @Size(max = 1000, message = "Instructions cannot exceed 1000 characters")
    private String instructions;

    public PrescriptionRequest() {
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
