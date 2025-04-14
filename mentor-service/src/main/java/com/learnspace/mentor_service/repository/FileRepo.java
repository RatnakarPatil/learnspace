package com.learnspace.mentor_service.repository;

import com.learnspace.mentor_service.pojos.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepo extends JpaRepository<File, Long> {
    List<File> findByClassroomClassroomId(Long classroomId);
}
