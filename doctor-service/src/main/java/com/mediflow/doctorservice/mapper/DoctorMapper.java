package com.mediflow.doctorservice.mapper;

import com.mediflow.doctorservice.dto.DoctorRequest;
import com.mediflow.doctorservice.dto.DoctorResponse;
import com.mediflow.doctorservice.entity.Doctor;


public final class DoctorMapper {


    private DoctorMapper() {
    }


    public static Doctor toEntity(
            DoctorRequest request) {


        Doctor doctor = new Doctor();


        doctor.setName(
                request.getName()
        );


        doctor.setEmail(
                request.getEmail()
        );


        doctor.setPhone(
                request.getPhone()
        );


        doctor.setSpecialization(
                request.getSpecialization()
        );


        doctor.setConsultationFee(
                request.getConsultationFee()
        );


        doctor.setAvailable(
                request.getAvailable()
        );


        return doctor;
    }


    public static DoctorResponse toResponse(
            Doctor doctor) {


        return new DoctorResponse(

                doctor.getId(),

                doctor.getName(),

                doctor.getEmail(),

                doctor.getPhone(),

                doctor.getSpecialization(),

                doctor.getConsultationFee(),

                doctor.getAvailable()

        );
    }


    public static void updateEntity(

            Doctor doctor,

            DoctorRequest request) {


        doctor.setName(
                request.getName()
        );


        doctor.setEmail(
                request.getEmail()
        );


        doctor.setPhone(
                request.getPhone()
        );


        doctor.setSpecialization(
                request.getSpecialization()
        );


        doctor.setConsultationFee(
                request.getConsultationFee()
        );


        doctor.setAvailable(
                request.getAvailable()
        );
    }
}
