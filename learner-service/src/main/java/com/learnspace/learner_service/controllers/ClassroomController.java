package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.dtos.ClassroomDTO;
import com.learnspace.learner_service.dtos.FileDTO;
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            String response = classroomService.joinClassroom(learnerUniqueId, classroomCode);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/myClassrooms")
    public ResponseEntity<?> getClassrooms(HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");

        if (learnerUniqueId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            List<ClassroomDTO> classrooms = classroomService.getClassroomsForLearner(learnerUniqueId);
            return ResponseEntity.ok(classrooms);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/classrooms/{classroomId}")
    public ResponseEntity<?> getClassroom(@PathVariable Long classroomId, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");

        if (learnerUniqueId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            ClassroomDTO dto = classroomService.getSpecifiedClassroom(learnerUniqueId, classroomId);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/classrooms/{classroomId}/files")
    public ResponseEntity<?> getFiles(@PathVariable Long classroomId, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");

        if (learnerUniqueId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            List<FileDTO> files = classroomService.getFilesForClassroomOfLearner(learnerUniqueId, classroomId);
            return ResponseEntity.ok(files);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/leaveClassroom/{classroomId}")
    public ResponseEntity<String> leaveClassroom(@PathVariable Long classroomId, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");

        if (learnerUniqueId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            String result = classroomService.leaveClassroom(learnerUniqueId, classroomId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
