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

    public boolean signup(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return false;  // User already exists
        }
        userRepo.save(user);
        return true;
    }

    public boolean login(String email, String password) {
        User user = userRepo.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public Role getUserRole(String email) {
        User user = userRepo.findByEmail(email);
        return (user != null) ? user.getRole() : null;
    }
}
