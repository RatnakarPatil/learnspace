package com.learnspace.mentor_service.controllers;

import com.learnspace.mentor_service.pojos.LoginRequest;
import com.learnspace.mentor_service.pojos.User;
import com.learnspace.mentor_service.services.MentorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MentorController {
    @Autowired
    private MentorService mentorService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User mentor) {
        boolean result = mentorService.signup(mentor);
        if (result) {
            return ResponseEntity.ok("Signup Successful");
        } else {
            return ResponseEntity.badRequest().body("Signup Failed: Email already in use");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        boolean isValid = mentorService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (isValid) {
            long mentorId = mentorService.getUserId(loginRequest.getEmail());
            session.setAttribute("mentorUniqueId", mentorId); // Store in session
            return ResponseEntity.ok().body("{\"message\": \"Login Successful\", \"mentorId\": " + mentorId + "}");
        } else {
            return ResponseEntity.status(401).body("{\"message\": \"Invalid email or password\"}");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Clear session
        return ResponseEntity.ok("Logged out successfully");
    }
}
