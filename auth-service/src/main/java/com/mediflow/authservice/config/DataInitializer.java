package com.mediflow.authservice.config;

import com.mediflow.authservice.entity.User;
import com.mediflow.authservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(UserRepository userRepository) {

        return args -> {

            if (userRepository.count() == 0) {

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                User admin = new User();
                admin.setFullName("System Administrator");
                admin.setEmail("admin@mediflow.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ADMIN");

                User doctor = new User();
                doctor.setFullName("Dr. Ananya Gupta");
                doctor.setEmail("ananya.gupta@mediflow.com");
                doctor.setPassword(encoder.encode("doctor123"));
                doctor.setRole("DOCTOR");

                User patient = new User();
                patient.setFullName("Rahul Sharma");
                patient.setEmail("rahul@example.com");
                patient.setPassword(encoder.encode("patient123"));
                patient.setRole("PATIENT");

                userRepository.save(admin);
                userRepository.save(doctor);
                userRepository.save(patient);
            }
        };
    }
}
