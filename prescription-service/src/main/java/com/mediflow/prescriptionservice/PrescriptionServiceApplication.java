package com.mediflow.prescriptionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PrescriptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrescriptionServiceApplication.class, args);
    }
}
