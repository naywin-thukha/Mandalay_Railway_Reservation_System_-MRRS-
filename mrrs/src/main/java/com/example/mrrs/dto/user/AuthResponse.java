package com.example.mrrs.dto.user;

import lombok.Data;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class AuthResponse {
    private String message;
    private String role;
    private String nrcId;
}
