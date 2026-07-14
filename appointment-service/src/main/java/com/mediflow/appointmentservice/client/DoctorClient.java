package com.mediflow.appointmentservice.client;

import com.mediflow.appointmentservice.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOCTOR-SERVICE")
public interface DoctorClient {

    @GetMapping("/doctors/{id}")
    DoctorResponse getDoctorById(@PathVariable("id") Long id);
}
