package com.mediflow.prescriptionservice.exception;

public class InvalidPrescriptionException extends RuntimeException {

    public InvalidPrescriptionException(String message) {
        super(message);
    }
}
