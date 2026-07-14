package com.mediflow.prescriptionservice.dto;

import java.time.LocalDate;

public class PrescriptionResponse {

    private Long id;

    private Long appointmentId;

    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;
    private String doctorSpecialization;

    private String diagnosis;
    private String medicines;
    private String dosage;
    private String instructions;

    private LocalDate prescriptionDate;

    public PrescriptionResponse() {
    }

    public PrescriptionResponse(
            Long id,
            Long appointmentId,
            Long patientId,
            String patientName,
            Long doctorId,
            String doctorName,
            String doctorSpecialization,
            String diagnosis,
            String medicines,
            String dosage,
            String instructions,
            LocalDate prescriptionDate) {

        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorSpecialization = doctorSpecialization;
        this.diagnosis = diagnosis;
        this.medicines = medicines;
        this.dosage = dosage;
        this.instructions = instructions;
        this.prescriptionDate = prescriptionDate;
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getMedicines() {
        return medicines;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }
}
