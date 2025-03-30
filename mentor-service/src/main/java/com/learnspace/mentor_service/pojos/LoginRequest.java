package com.learnspace.mentor_service.pojos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;   // ✅ Changed `mentorEmail` to generic `email`
    private String password;  // ✅ Changed `mentorPassword` to `password`
}
