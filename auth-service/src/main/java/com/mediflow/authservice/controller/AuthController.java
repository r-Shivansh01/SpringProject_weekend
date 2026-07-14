package com.mediflow.authservice.controller;

import com.mediflow.authservice.dto.AuthResponse;
import com.mediflow.authservice.dto.LoginRequest;
import com.mediflow.authservice.dto.RegisterRequest;
import com.mediflow.authservice.dto.TokenValidationResponse;
import com.mediflow.authservice.dto.UserResponse;
import com.mediflow.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validate(
            @RequestBody Map<String, String> body) {

        return ResponseEntity.ok(authService.validateToken(body.get("token")));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(authService.getUserById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        authService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
