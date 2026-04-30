package com.example.mrrs.dto.user;

import lombok.Data;
import lombok.Builder;

public class AuthResponse {
    private String message;
    private String role;
    private String nrcId;
}
