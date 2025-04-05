package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepo extends JpaRepository<Classroom, Long> {
    Classroom findByClassroomCode(String classroomCode);
}
