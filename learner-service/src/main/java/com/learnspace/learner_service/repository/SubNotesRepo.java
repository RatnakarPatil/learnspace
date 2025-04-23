package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.SubNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubNotesRepo extends JpaRepository<SubNotes, Long> {
}

