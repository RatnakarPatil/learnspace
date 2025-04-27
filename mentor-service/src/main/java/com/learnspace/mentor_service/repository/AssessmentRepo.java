package com.learnspace.mentor_service.repository;

import com.learnspace.mentor_service.pojos.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepo extends JpaRepository<Assessment, Long> {
    List<Assessment> findByClassroom_ClassroomId(Long classroomId);
}