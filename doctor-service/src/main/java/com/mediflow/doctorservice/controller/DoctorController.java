package com.mediflow.doctorservice.controller;


import com.mediflow.doctorservice.dto.DoctorRequest;

import com.mediflow.doctorservice.dto.DoctorResponse;

import com.mediflow.doctorservice.service.DoctorService;


import jakarta.validation.Valid;


import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/doctors")
public class DoctorController {


    private final DoctorService doctorService;


    public DoctorController(
            DoctorService doctorService) {

        this.doctorService =
                doctorService;
    }


    @PostMapping
    public ResponseEntity<DoctorResponse>
    createDoctor(

            @Valid
            @RequestBody
            DoctorRequest request) {


        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(
                        doctorService
                                .createDoctor(request)
                );
    }


    @GetMapping
    public ResponseEntity<List<DoctorResponse>>
    getAllDoctors() {


        return ResponseEntity.ok(

                doctorService.getAllDoctors()

        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse>
    getDoctorById(

            @PathVariable Long id) {


        return ResponseEntity.ok(

                doctorService
                        .getDoctorById(id)

        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse>
    updateDoctor(

            @PathVariable Long id,

            @Valid
            @RequestBody
            DoctorRequest request) {


        return ResponseEntity.ok(

                doctorService.updateDoctor(
                        id,
                        request
                )

        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteDoctor(

            @PathVariable Long id) {


        doctorService.deleteDoctor(id);


        return ResponseEntity

                .noContent()

                .build();
    }


    @GetMapping(
            "/specialization/{specialization}"
    )
    public ResponseEntity<List<DoctorResponse>>
    getDoctorsBySpecialization(

            @PathVariable
            String specialization) {


        return ResponseEntity.ok(

                doctorService
                        .getDoctorsBySpecialization(
                                specialization
                        )

        );
    }


    @GetMapping("/available")
    public ResponseEntity<List<DoctorResponse>>
    getAvailableDoctors() {


        return ResponseEntity.ok(

                doctorService
                        .getAvailableDoctors()

        );
    }


    @GetMapping(
            "/available/specialization/{specialization}"
    )
    public ResponseEntity<List<DoctorResponse>>
    getAvailableDoctorsBySpecialization(

            @PathVariable
            String specialization) {


        return ResponseEntity.ok(

                doctorService
                        .getAvailableDoctorsBySpecialization(
                                specialization
                        )

        );
    }
}
