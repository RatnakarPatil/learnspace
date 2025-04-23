package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepo extends JpaRepository<Assessment, Long> {
    List<Assessment> findByCreatedByUserId(Long learnerId);
}
