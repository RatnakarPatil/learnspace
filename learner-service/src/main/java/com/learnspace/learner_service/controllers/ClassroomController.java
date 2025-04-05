package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.dtos.ClassroomDTO;
import com.learnspace.learner_service.dtos.FileDTO;
import com.learnspace.learner_service.pojos.Classroom;
import com.learnspace.learner_service.services.ClassroomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/joinClassroom/{classroomCode}")
    public ResponseEntity<String> joinClassroom(@PathVariable String classroomCode, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");

        if (learnerUniqueId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        try {
            String response = classroomService.joinClassroom(learnerUniqueId, classroomCode);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/myClassrooms")
    public ResponseEntity<List<ClassroomDTO>> getClassrooms(HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");
        if (learnerUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getClassroomsForLearner(learnerUniqueId));
    }

    @GetMapping("/classrooms/{classroomId}")
    public ResponseEntity<ClassroomDTO> getClassroom(@PathVariable Long classroomId, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");
        if (learnerUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getSpecifiedClassroom(learnerUniqueId, classroomId));
    }

    @GetMapping("/classrooms/{classroomId}/files")
    public ResponseEntity<List<FileDTO>> getFiles(@PathVariable Long classroomId, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");
        if (learnerUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getFilesForClassroomOfLearner(learnerUniqueId, classroomId));
    }
}
