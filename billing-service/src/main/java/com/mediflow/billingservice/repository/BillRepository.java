package com.mediflow.billingservice.repository;

import com.mediflow.billingservice.entity.Bill;
import com.mediflow.billingservice.entity.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    boolean existsByAppointmentId(Long appointmentId);

    Optional<Bill> findByAppointmentId(Long appointmentId);

    List<Bill> findByPatientId(Long patientId);

    List<Bill> findByStatus(BillStatus status);
}
