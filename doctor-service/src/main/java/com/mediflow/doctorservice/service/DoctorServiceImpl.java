package com.mediflow.doctorservice.service;


import com.mediflow.doctorservice.dto.DoctorRequest;

import com.mediflow.doctorservice.dto.DoctorResponse;

import com.mediflow.doctorservice.entity.Doctor;

import com.mediflow.doctorservice.exception
        .DuplicateResourceException;

import com.mediflow.doctorservice.exception
        .ResourceNotFoundException;

import com.mediflow.doctorservice.mapper.DoctorMapper;

import com.mediflow.doctorservice.repository
        .DoctorRepository;


import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation
        .Transactional;


import java.util.List;


@Service
@Transactional
public class DoctorServiceImpl
        implements DoctorService {


    private final DoctorRepository
            doctorRepository;


    public DoctorServiceImpl(
            DoctorRepository doctorRepository) {

        this.doctorRepository =
                doctorRepository;
    }


    @Override
    public DoctorResponse createDoctor(
            DoctorRequest request) {


        if (doctorRepository.existsByEmail(
                request.getEmail())) {


            throw new DuplicateResourceException(

                    "Doctor already exists with email: "
                            + request.getEmail()

            );
        }


        Doctor doctor =
                DoctorMapper.toEntity(request);


        Doctor savedDoctor =
                doctorRepository.save(doctor);


        return DoctorMapper.toResponse(
                savedDoctor
        );
    }


    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse>
    getAllDoctors() {


        return doctorRepository
                .findAll()
                .stream()
                .map(DoctorMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(
            Long id) {


        return DoctorMapper.toResponse(
                findDoctor(id)
        );
    }


    @Override
    public DoctorResponse updateDoctor(

            Long id,

            DoctorRequest request) {


        Doctor doctor =
                findDoctor(id);


        doctorRepository
                .findByEmail(request.getEmail())
                .filter(existingDoctor ->

                        !existingDoctor
                                .getId()
                                .equals(id)

                )
                .ifPresent(existingDoctor -> {


                    throw new DuplicateResourceException(

                            "Another doctor already uses email: "
                                    + request.getEmail()

                    );
                });


        DoctorMapper.updateEntity(
                doctor,
                request
        );


        Doctor updatedDoctor =
                doctorRepository.save(doctor);


        return DoctorMapper.toResponse(
                updatedDoctor
        );
    }


    @Override
    public void deleteDoctor(
            Long id) {


        Doctor doctor =
                findDoctor(id);


        doctorRepository.delete(doctor);
    }


    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse>
    getDoctorsBySpecialization(
            String specialization) {


        return doctorRepository

                .findBySpecializationIgnoreCase(
                        specialization
                )

                .stream()

                .map(DoctorMapper::toResponse)

                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse>
    getAvailableDoctors() {


        return doctorRepository

                .findByAvailableTrue()

                .stream()

                .map(DoctorMapper::toResponse)

                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse>
    getAvailableDoctorsBySpecialization(
            String specialization) {


        return doctorRepository

                .findBySpecializationIgnoreCaseAndAvailableTrue(
                        specialization
                )

                .stream()

                .map(DoctorMapper::toResponse)

                .toList();
    }


    private Doctor findDoctor(
            Long id) {


        return doctorRepository

                .findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Doctor not found with id: "
                                        + id

                        )

                );
    }
}
