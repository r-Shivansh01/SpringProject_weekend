package com.mediflow.billingservice.client;

import com.mediflow.billingservice.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PATIENT-SERVICE")
public interface PatientClient {

    @GetMapping("/patients/{id}")
    PatientResponse getPatientById(
            @PathVariable("id") Long id
    );
}
