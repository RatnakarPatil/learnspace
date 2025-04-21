package com.learnspace.auth_service.controllers;

import com.learnspace.auth_service.dtos.LoginRequest;
import com.learnspace.auth_service.pojos.User;
import com.learnspace.auth_service.services.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        boolean isRegistered = authService.signup(user);
        if (isRegistered) {
            return ResponseEntity.ok("Signup Successful");
        } else {
            return ResponseEntity.badRequest().body("Signup Failed: Email already in use");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = authService.login(request.getEmail(), request.getPassword(), request.getRole());
        if (user != null) {

            if (user.getRole().name().equals("MENTOR")) {
                session.setAttribute("mentorUniqueId", user.getUserId()); // ✅ used in mentor-service
            }

            if (user.getRole().name().equals("LEARNER")) {
                session.setAttribute("learnerUniqueId", user.getUserId()); // ✅ used in learner-service
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("userId", user.getUserId());
            response.put("role", user.getRole().name());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Invalid credentials or role mismatch"));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}
