package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepo extends JpaRepository<Notes, Long> {
    List<Notes> findAllByLearner_UserId(Long learnerId);
}
