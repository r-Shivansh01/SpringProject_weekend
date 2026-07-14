package com.mediflow.authservice.service;

import com.mediflow.authservice.dto.AuthResponse;
import com.mediflow.authservice.dto.LoginRequest;
import com.mediflow.authservice.dto.RegisterRequest;
import com.mediflow.authservice.dto.TokenValidationResponse;
import com.mediflow.authservice.dto.UserResponse;
import com.mediflow.authservice.entity.User;
import com.mediflow.authservice.exception.DuplicateResourceException;
import com.mediflow.authservice.exception.InvalidCredentialsException;
import com.mediflow.authservice.exception.ResourceNotFoundException;
import com.mediflow.authservice.mapper.UserMapper;
import com.mediflow.authservice.repository.UserRepository;
import com.mediflow.authservice.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "An account already exists with email: " + request.getEmail()
            );
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().toUpperCase());

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, jwtUtil.getExpirationMs(), UserMapper.toResponse(user));
    }

    @Override
    @Transactional(readOnly = true)
    public TokenValidationResponse validateToken(String token) {

        try {
            Claims claims = jwtUtil.parseClaims(token);

            return new TokenValidationResponse(
                    true,
                    claims.getSubject(),
                    claims.get("role", String.class),
                    "Token is valid"
            );
        } catch (JwtException | IllegalArgumentException exception) {
            return new TokenValidationResponse(false, null, null, "Token is invalid or expired");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        return UserMapper.toResponse(findUser(id));
    }

    @Override
    public void deleteUser(Long id) {

        User user = findUser(id);

        userRepository.delete(user);
    }

    private User findUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }
}
