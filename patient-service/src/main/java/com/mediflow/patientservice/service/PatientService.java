package com.mediflow.patientservice.service;

import com.mediflow.patientservice.dto.PatientRequest;
import com.mediflow.patientservice.dto.PatientResponse;

import java.util.List;

public interface PatientService {

    PatientResponse createPatient(PatientRequest request);

    List<PatientResponse> getAllPatients();

    PatientResponse getPatientById(Long id);

    PatientResponse updatePatient(Long id, PatientRequest request);

    void deletePatient(Long id);
}
