package com.learnspace.auth_service.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String role;
}
