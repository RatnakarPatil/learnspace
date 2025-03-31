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
            return false; // Email already exists
        }
        ur.save(user);
        return true;
    }

    public boolean login(String email, String password) {
        User user = ur.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public User.Role getUserRole(String email) {
        User user = ur.findByEmail(email);
        return (user != null) ? user.getRole() : null;
    }
}
