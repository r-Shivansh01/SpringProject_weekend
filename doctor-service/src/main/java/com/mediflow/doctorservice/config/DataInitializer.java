package com.mediflow.doctorservice.config;


import com.mediflow.doctorservice.entity.Doctor;

import com.mediflow.doctorservice.repository
        .DoctorRepository;


import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;


@Configuration
public class DataInitializer {


    @Bean
    CommandLineRunner initializeDoctors(

            DoctorRepository doctorRepository) {


        return args -> {


            if (doctorRepository.count() == 0) {


                Doctor doctor1 =
                        new Doctor(

                                null,

                                "Dr. Amit Sharma",

                                "amit.sharma@mediflow.com",

                                "9876543201",

                                "Cardiology",

                                800.0,

                                true

                        );


                Doctor doctor2 =
                        new Doctor(

                                null,

                                "Dr. Neha Verma",

                                "neha.verma@mediflow.com",

                                "9876543202",

                                "Dermatology",

                                600.0,

                                true

                        );


                Doctor doctor3 =
                        new Doctor(

                                null,

                                "Dr. Raj Singh",

                                "raj.singh@mediflow.com",

                                "9876543203",

                                "Cardiology",

                                1000.0,

                                false

                        );


                doctorRepository.save(doctor1);

                doctorRepository.save(doctor2);

                doctorRepository.save(doctor3);
            }
        };
    }
}
