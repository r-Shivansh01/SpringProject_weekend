package com.mediflow.prescriptionservice.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "prescriptions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_prescription_appointment",
                        columnNames = "appointmentId"
                )
        }
)
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long appointmentId;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false, length = 500)
    private String diagnosis;

    @Column(nullable = false, length = 1000)
    private String medicines;

    @Column(nullable = false, length = 500)
    private String dosage;

    @Column(nullable = false, length = 1000)
    private String instructions;

    @Column(nullable = false)
    private LocalDate prescriptionDate;

    public Prescription() {
    }

    public Prescription(
            Long id,
            Long appointmentId,
            Long patientId,
            Long doctorId,
            String diagnosis,
            String medicines,
            String dosage,
            String instructions,
            LocalDate prescriptionDate) {

        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.medicines = medicines;
        this.dosage = dosage;
        this.instructions = instructions;
        this.prescriptionDate = prescriptionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }
}
