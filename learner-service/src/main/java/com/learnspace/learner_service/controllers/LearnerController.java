package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.pojos.LoginRequest;
import com.learnspace.learner_service.pojos.User;
import com.learnspace.learner_service.services.LearnerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LearnerController {

    @Autowired
    private LearnerService learnerService;

    @PostMapping("/signup")
    public ResponseEntity<?> doSignup(@RequestBody User learner) {
        User savedUser = learnerService.signup(learner);
        if (savedUser != null) {
            return ResponseEntity.ok(Map.of(
                    "userId", savedUser.getUserId(), // Assuming mentor object has userId
                    "role", savedUser.getRole() // Assuming mentor object has role
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Signup Failed: Email already in use"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest) {
        String loginStatus = learnerService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword(),
                loginRequest.getRole()
        );

        if (loginStatus.equals("SUCCESS")) {
            long learnerId = learnerService.getUserId(loginRequest.getEmail());
            String role = loginRequest.getRole();
            return ResponseEntity.ok(Map.of(
                    "message", "Login Successful",
                    "userId", learnerId,
                    "role", role
            ));
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Invalid credentials", loginStatus);
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam Long userId) {
        return ResponseEntity.ok("Logged out successfully for userId: " + userId);
    }
}