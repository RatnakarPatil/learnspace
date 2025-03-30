package com.learnspace.learner_service.repository;

import com.learnspace.learner_service.pojos.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepo extends JpaRepository<File, Long> {
}
