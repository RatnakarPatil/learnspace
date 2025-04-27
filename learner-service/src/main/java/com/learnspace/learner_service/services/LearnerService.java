package com.learnspace.learner_service.services;

import com.learnspace.learner_service.pojos.User;
import com.learnspace.learner_service.pojos.User.Role;
import com.learnspace.learner_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearnerService {

    @Autowired
    private UserRepo userRepo;

    public long getUserId(String email) {
        User user = userRepo.findByEmail(email);
        return (user != null) ? user.getUserId() : -1;
    }

    public User signup(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return null;  // User already exists
        }
        return userRepo.save(user); // ✅ Save and return the saved User
    }


    public String login(String email, String password, String role) {
        User user = userRepo.findByEmail(email);

        if (user == null) {
            return "User not found";
        }

        if (!user.getRole().name().equalsIgnoreCase(role)) {
            return "Role mismatch. You are registered as a " + user.getRole().name();
        }

        if (!user.getPassword().equals(password)) {
            return "Invalid password";
        }

        return "SUCCESS";
    }


    public Role getUserRole(String email) {
        User user = userRepo.findByEmail(email);
        return (user != null) ? user.getRole() : null;
    }
}