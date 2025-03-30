package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepo extends JpaRepository<Question, Long> {
}
