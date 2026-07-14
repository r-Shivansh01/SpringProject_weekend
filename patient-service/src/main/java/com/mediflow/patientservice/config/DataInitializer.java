package com.mediflow.patientservice.config;

import com.mediflow.patientservice.entity.Patient;
import com.mediflow.patientservice.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializePatients(
            PatientRepository patientRepository) {

        return args -> {

            if (patientRepository.count() == 0) {

                Patient patient1 = new Patient(
                        null,
                        "Rahul Sharma",
                        "rahul@example.com",
                        "9876543210",
                        "Male",
                        25,
                        "Punjab, India"
                );

                Patient patient2 = new Patient(
                        null,
                        "Priya Verma",
                        "priya@example.com",
                        "9876501234",
                        "Female",
                        30,
                        "Delhi, India"
                );

                patientRepository.save(patient1);
                patientRepository.save(patient2);
            }
        };
    }
}
