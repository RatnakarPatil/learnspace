package com.learnspace.auth_service.services;

import com.learnspace.auth_service.pojos.User;
import com.learnspace.auth_service.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;

    public boolean signup(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return false; // User already exists
        }
        userRepo.save(user);
        return true;
    }

    public User login(String email, String password, String role) {
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getRole().name().equalsIgnoreCase(role)) {
            return user;
        }
        return null;
    }
}

