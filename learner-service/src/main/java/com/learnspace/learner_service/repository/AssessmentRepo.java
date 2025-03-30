package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepo extends JpaRepository<Assessment, Long> {
}
