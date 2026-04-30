package com.example.mrrs.dto.user;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nrcId;
    private String fullName;
    private String phone;
    private String email;
    private String password;
}
