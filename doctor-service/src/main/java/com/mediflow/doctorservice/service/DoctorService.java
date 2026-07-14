package com.mediflow.doctorservice.service;


import com.mediflow.doctorservice.dto.DoctorRequest;

import com.mediflow.doctorservice.dto.DoctorResponse;


import java.util.List;


public interface DoctorService {


    DoctorResponse createDoctor(
            DoctorRequest request
    );


    List<DoctorResponse> getAllDoctors();


    DoctorResponse getDoctorById(
            Long id
    );


    DoctorResponse updateDoctor(
            Long id,
            DoctorRequest request
    );


    void deleteDoctor(
            Long id
    );


    List<DoctorResponse>
    getDoctorsBySpecialization(
            String specialization
    );


    List<DoctorResponse>
    getAvailableDoctors();


    List<DoctorResponse>
    getAvailableDoctorsBySpecialization(
            String specialization
    );
}
