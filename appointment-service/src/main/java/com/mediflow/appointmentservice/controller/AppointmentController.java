package com.mediflow.appointmentservice.controller;

import com.mediflow.appointmentservice.dto.AppointmentRequest;
import com.mediflow.appointmentservice.dto.AppointmentResponse;
import com.mediflow.appointmentservice.dto.AppointmentStatusRequest;
import com.mediflow.appointmentservice.entity.AppointmentStatus;
import com.mediflow.appointmentservice.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @Valid @RequestBody AppointmentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointmentService.createAppointment(request));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>>
    getAllAppointments() {

        return ResponseEntity.ok(
                appointmentService.getAllAppointments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse>
    getAppointmentById(@PathVariable Long id) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentById(id)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponse>>
    getAppointmentsByPatientId(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsByPatientId(patientId)
        );
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponse>>
    getAppointmentsByDoctorId(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsByDoctorId(doctorId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentResponse>>
    getAppointmentsByStatus(
            @PathVariable AppointmentStatus status) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentsByStatus(status)
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse>
    updateAppointmentStatus(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentStatusRequest request) {

        return ResponseEntity.ok(
                appointmentService.updateAppointmentStatus(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.noContent().build();
    }
}
