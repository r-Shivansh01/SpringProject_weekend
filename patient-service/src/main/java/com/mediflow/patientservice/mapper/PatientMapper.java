package com.mediflow.patientservice.mapper;

import com.mediflow.patientservice.dto.PatientRequest;
import com.mediflow.patientservice.dto.PatientResponse;
import com.mediflow.patientservice.entity.Patient;

public final class PatientMapper {

    private PatientMapper() {
    }

    public static Patient toEntity(PatientRequest request) {

        Patient patient = new Patient();

        patient.setName(request.getName());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setGender(request.getGender());
        patient.setAge(request.getAge());
        patient.setAddress(request.getAddress());

        return patient;
    }

    public static PatientResponse toResponse(Patient patient) {

        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getGender(),
                patient.getAge(),
                patient.getAddress()
        );
    }

    public static void updateEntity(
            Patient patient,
            PatientRequest request) {

        patient.setName(request.getName());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setGender(request.getGender());
        patient.setAge(request.getAge());
        patient.setAddress(request.getAddress());
    }
}
