package com.learnspace.mentor_service.repository;

import com.learnspace.mentor_service.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
