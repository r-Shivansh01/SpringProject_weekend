package com.mediflow.prescriptionservice.client;

import com.mediflow.prescriptionservice.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOCTOR-SERVICE")
public interface DoctorClient {

    @GetMapping("/doctors/{id}")
    DoctorResponse getDoctorById(@PathVariable("id") Long id);
}
