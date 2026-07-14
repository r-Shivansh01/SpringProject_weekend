package com.mediflow.prescriptionservice.client;

import com.mediflow.prescriptionservice.dto.AppointmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "APPOINTMENT-SERVICE")
public interface AppointmentClient {

    @GetMapping("/appointments/{id}")
    AppointmentResponse getAppointmentById(@PathVariable("id") Long id);
}
