package com.learnspace.learner_service.pojos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String role;
}
