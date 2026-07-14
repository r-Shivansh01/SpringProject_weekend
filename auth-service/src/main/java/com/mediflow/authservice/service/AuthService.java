package com.mediflow.authservice.service;

import com.mediflow.authservice.dto.AuthResponse;
import com.mediflow.authservice.dto.LoginRequest;
import com.mediflow.authservice.dto.RegisterRequest;
import com.mediflow.authservice.dto.TokenValidationResponse;
import com.mediflow.authservice.dto.UserResponse;

import java.util.List;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    TokenValidationResponse validateToken(String token);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUser(Long id);
}
