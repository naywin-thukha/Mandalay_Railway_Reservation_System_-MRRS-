package com.example.mrrs.dto.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String nrcId;
    private String password;
}
