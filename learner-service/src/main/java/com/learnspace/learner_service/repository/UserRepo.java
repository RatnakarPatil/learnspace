package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByUserIdAndClassrooms_ClassroomId(Long learnerId, Long classroomId);
}
