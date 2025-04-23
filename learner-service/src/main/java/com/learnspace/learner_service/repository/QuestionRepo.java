package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findByNextRevisionDateAndIsActive(LocalDate date, boolean isActive);
}
