package com.mediflow.prescriptionservice.controller;

import com.mediflow.prescriptionservice.dto.PrescriptionRequest;
import com.mediflow.prescriptionservice.dto.PrescriptionResponse;
import com.mediflow.prescriptionservice.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(
            PrescriptionService prescriptionService) {

        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionResponse> createPrescription(
            @Valid @RequestBody PrescriptionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(prescriptionService.createPrescription(request));
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionResponse>>
    getAllPrescriptions() {

        return ResponseEntity.ok(
                prescriptionService.getAllPrescriptions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponse>
    getPrescriptionById(@PathVariable Long id) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionById(id)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionResponse>>
    getPrescriptionsByPatientId(@PathVariable Long patientId) {

        return ResponseEntity.ok(
                prescriptionService
                        .getPrescriptionsByPatientId(patientId)
        );
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<PrescriptionResponse>>
    getPrescriptionsByDoctorId(@PathVariable Long doctorId) {

        return ResponseEntity.ok(
                prescriptionService
                        .getPrescriptionsByDoctorId(doctorId)
        );
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<PrescriptionResponse>>
    getPrescriptionsByAppointmentId(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                prescriptionService
                        .getPrescriptionsByAppointmentId(appointmentId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(
            @PathVariable Long id) {

        prescriptionService.deletePrescription(id);

        return ResponseEntity.noContent().build();
    }
}
