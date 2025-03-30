package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.pojos.LoginRequest;
import com.learnspace.learner_service.pojos.User;
import com.learnspace.learner_service.services.LearnerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LearnerController {

    @Autowired
    private LearnerService learnerService;

    @PostMapping("/signup")
    public ResponseEntity<String> doSignup(@RequestBody User learner) {
        boolean result = learnerService.signup(learner);
        if (result) {
            return ResponseEntity.ok("Signup Successful");
        } else {
            return ResponseEntity.badRequest().body("Signup Failed: Email already in use");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest, HttpSession session) {
        boolean isValid = learnerService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (isValid) {
            long learnerId = learnerService.getUserId(loginRequest.getEmail());
            session.setAttribute("learnerUniqueId", learnerId); // Store in session
            return ResponseEntity.ok().body("{\"message\": \"Login Successful\", \"learnerId\": " + learnerId + "}");
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
