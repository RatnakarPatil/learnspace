package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.Classroom;
import com.learnspace.learner_service.pojos.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepo extends JpaRepository<File, Long> {
    List<File> findByClassroom(Classroom classroom);
}