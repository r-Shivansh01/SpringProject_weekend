package com.mediflow.notificationservice.dto;

public class PatientResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private Integer age;
    private String address;

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

    public String getPhone() {
        return phone;
    }
}
