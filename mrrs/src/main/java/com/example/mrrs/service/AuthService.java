package com.example.mrrs.service;

import com.example.mrrs.dto.user.LoginRequest;
import com.example.mrrs.dto.user.AuthResponse;
import com.example.mrrs.dto.user.RegisterRequest;
import com.example.mrrs.entity.User;
import com.example.mrrs.entity.Role;
import com.example.mrrs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request){
        if (userRepository.existsByNrcId(request.getNrcId())){
            throw new RuntimeException("NRC already registered");
        }
        User user = User.builder()
                .nrcId(request.getNrcId())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .passwordHash(PasswordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .message("User registered successfully")
                .role(user.getRole().name())
                .nrcId(user.getNrcId())
                .build();
    }
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByNrcId(request.getNrcId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        return AuthResponse.builder()
                .message("Login successful")
                .role(user.getRole().name())
                .nrcId(user.getNrcId())
                .build();
    }
}
