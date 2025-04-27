package com.learnspace.mentor_service.controllers;

import com.learnspace.mentor_service.pojos.LoginRequest;
import com.learnspace.mentor_service.pojos.User;
import com.learnspace.mentor_service.services.MentorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MentorController {
    @Autowired
    private MentorService mentorService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody User mentor) {
        boolean result = mentorService.signup(mentor);
        if (result) {
            // Return userId and role in response
            return ResponseEntity.ok(Map.of(
                    "userId", mentor.getUserId(), // Assuming mentor object has userId
                    "role", mentor.getRole() // Assuming mentor object has role
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Signup Failed: Email already in use"));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        String result = mentorService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword(),
                loginRequest.getRole()
        );

        if (result.equals("SUCCESS")) {
            long mentorId = mentorService.getUserId(loginRequest.getEmail());
            String role = loginRequest.getRole(); // Get the role from the request
            // Return the mentorId and role in the response
            return ResponseEntity.ok(Map.of(
                    "message", "Login Successful",
                    "userId", mentorId,
                    "role", role
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", result));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam Long userId) {
        return ResponseEntity.ok("Logged out successfully for userId: " + userId);
    }
}