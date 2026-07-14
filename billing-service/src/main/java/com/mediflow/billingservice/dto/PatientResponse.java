package com.mediflow.billingservice.dto;

public class PatientResponse {

    private Long id;
    private String name;
    private String email;

    public PatientResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
