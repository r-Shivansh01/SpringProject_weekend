package com.mediflow.doctorservice.repository;

import com.mediflow.doctorservice.entity.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DoctorRepository
        extends JpaRepository<Doctor, Long> {


    Optional<Doctor> findByEmail(String email);


    boolean existsByEmail(String email);


    List<Doctor>
    findBySpecializationIgnoreCase(String specialization);


    List<Doctor>
    findByAvailableTrue();


    List<Doctor>
    findBySpecializationIgnoreCaseAndAvailableTrue(
            String specialization
    );
}
