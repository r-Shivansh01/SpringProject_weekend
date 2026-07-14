package com.mediflow.apigateway.dto;

/**
 * Mirrors auth-service's TokenValidationResponse so the gateway can
 * deserialize the JSON body returned by POST /auth/validate.
 */
public class TokenValidationResult {

    private boolean valid;
    private String email;
    private String role;
    private String message;

    public TokenValidationResult() {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
