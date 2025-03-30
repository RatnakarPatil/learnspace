package com.learnspace.mentor_service.controllers;

import com.learnspace.mentor_service.pojos.LoginRequest;
import com.learnspace.mentor_service.pojos.User;
import com.learnspace.mentor_service.services.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MentorController {
    @Autowired
    private MentorService mentorService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        return mentorService.signup(user) ? "Signup Successful" : "Signup Failed";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return mentorService.login(loginRequest.getEmail(), loginRequest.getPassword())
                ? "Login Successful" : "Login Failed";
    }
}
