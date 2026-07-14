package com.mediflow.patientservice.service;

import com.mediflow.patientservice.dto.PatientRequest;
import com.mediflow.patientservice.dto.PatientResponse;
import com.mediflow.patientservice.entity.Patient;
import com.mediflow.patientservice.exception.DuplicateResourceException;
import com.mediflow.patientservice.exception.ResourceNotFoundException;
import com.mediflow.patientservice.mapper.PatientMapper;
import com.mediflow.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponse createPatient(PatientRequest request) {

        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Patient already exists with email: " + request.getEmail()
            );
        }

        Patient patient = PatientMapper.toEntity(request);

        Patient savedPatient = patientRepository.save(patient);

        return PatientMapper.toResponse(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(PatientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {

        Patient patient = findPatient(id);

        return PatientMapper.toResponse(patient);
    }

    @Override
    public PatientResponse updatePatient(
            Long id,
            PatientRequest request) {

        Patient patient = findPatient(id);

        patientRepository.findByEmail(request.getEmail())
                .filter(existingPatient ->
                        !existingPatient.getId().equals(id))
                .ifPresent(existingPatient -> {
                    throw new DuplicateResourceException(
                            "Another patient already uses email: "
                                    + request.getEmail()
                    );
                });

        PatientMapper.updateEntity(patient, request);

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toResponse(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {

        Patient patient = findPatient(id);

        patientRepository.delete(patient);
    }

    private Patient findPatient(Long id) {

        return patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: " + id
                        )
                );
    }
}
