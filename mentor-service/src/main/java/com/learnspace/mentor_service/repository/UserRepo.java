package com.learnspace.mentor_service.repository;

import com.learnspace.mentor_service.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query(value = "SELECT u.email FROM users u " +
            "JOIN classroom_learners cl ON u.user_id = cl.learner_id " +
            "WHERE cl.classroom_id = :classroomId", nativeQuery = true)
    List<String> findLearnersEmailsByClassroomId(@Param("classroomId") Long classroomId);
}
