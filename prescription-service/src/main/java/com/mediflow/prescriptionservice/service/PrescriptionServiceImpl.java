package com.mediflow.prescriptionservice.service;

import com.mediflow.prescriptionservice.client.AppointmentClient;
import com.mediflow.prescriptionservice.client.DoctorClient;
import com.mediflow.prescriptionservice.client.PatientClient;
import com.mediflow.prescriptionservice.dto.AppointmentResponse;
import com.mediflow.prescriptionservice.dto.DoctorResponse;
import com.mediflow.prescriptionservice.dto.PatientResponse;
import com.mediflow.prescriptionservice.dto.PrescriptionRequest;
import com.mediflow.prescriptionservice.dto.PrescriptionResponse;
import com.mediflow.prescriptionservice.entity.Prescription;
import com.mediflow.prescriptionservice.exception.InvalidPrescriptionException;
import com.mediflow.prescriptionservice.exception.ResourceNotFoundException;
import com.mediflow.prescriptionservice.mapper.PrescriptionMapper;
import com.mediflow.prescriptionservice.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;
    private final AppointmentClient appointmentClient;

    public PrescriptionServiceImpl(
            PrescriptionRepository prescriptionRepository,
            PatientClient patientClient,
            DoctorClient doctorClient,
            AppointmentClient appointmentClient) {

        this.prescriptionRepository = prescriptionRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
        this.appointmentClient = appointmentClient;
    }

    @Override
    public PrescriptionResponse createPrescription(
            PrescriptionRequest request) {

        AppointmentResponse appointment =
                appointmentClient.getAppointmentById(
                        request.getAppointmentId()
                );

        PatientResponse patient =
                patientClient.getPatientById(request.getPatientId());

        DoctorResponse doctor =
                doctorClient.getDoctorById(request.getDoctorId());

        if (!appointment.getPatientId().equals(request.getPatientId())) {
            throw new InvalidPrescriptionException(
                    "Patient id does not match the appointment patient"
            );
        }

        if (!appointment.getDoctorId().equals(request.getDoctorId())) {
            throw new InvalidPrescriptionException(
                    "Doctor id does not match the appointment doctor"
            );
        }

        if (prescriptionRepository.existsByAppointmentId(
                request.getAppointmentId())) {

            throw new InvalidPrescriptionException(
                    "A prescription already exists for appointment id: "
                            + request.getAppointmentId()
            );
        }

        Prescription prescription =
                PrescriptionMapper.toEntity(request);

        Prescription savedPrescription =
                prescriptionRepository.save(prescription);

        return PrescriptionMapper.toResponse(
                savedPrescription,
                patient,
                doctor
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getAllPrescriptions() {

        return prescriptionRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionById(Long id) {

        return buildResponse(findPrescription(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByPatientId(
            Long patientId) {

        patientClient.getPatientById(patientId);

        return prescriptionRepository.findByPatientId(patientId)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByDoctorId(
            Long doctorId) {

        doctorClient.getDoctorById(doctorId);

        return prescriptionRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByAppointmentId(
            Long appointmentId) {

        appointmentClient.getAppointmentById(appointmentId);

        return prescriptionRepository.findByAppointmentId(appointmentId)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public void deletePrescription(Long id) {

        Prescription prescription = findPrescription(id);

        prescriptionRepository.delete(prescription);
    }

    private Prescription findPrescription(Long id) {

        return prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Prescription not found with id: " + id
                        )
                );
    }

    private PrescriptionResponse buildResponse(
            Prescription prescription) {

        PatientResponse patient =
                patientClient.getPatientById(
                        prescription.getPatientId()
                );

        DoctorResponse doctor =
                doctorClient.getDoctorById(
                        prescription.getDoctorId()
                );

        return PrescriptionMapper.toResponse(
                prescription,
                patient,
                doctor
        );
    }
}
