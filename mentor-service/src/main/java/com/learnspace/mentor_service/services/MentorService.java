package com.learnspace.mentor_service.services;

import com.learnspace.mentor_service.pojos.User;
import com.learnspace.mentor_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MentorService {

    @Autowired
    private UserRepo ur;

    public long getUserId(String email) {
        User user = ur.findByEmail(email);
        return (user != null) ? user.getUserId() : -1;
    }

    public boolean signup(User user) {
        if (ur.findByEmail(user.getEmail()) != null) {
            return false;
        }
        ur.save(user);
        return true;
    }

    public String login(String email, String password, String role) {
        User user = ur.findByEmail(email);

        if (user == null) {
            return "User not found";
        }

        if (!user.getRole().name().equalsIgnoreCase(role)) {
            return "Role mismatch. You are registered as a " + user.getRole();
        }

        if (!user.getPassword().equals(password)) {
            return "Invalid password";
        }

        return "SUCCESS";
    }
}