package com.learnspace.mentor_service.repository;

import com.learnspace.mentor_service.pojos.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {}