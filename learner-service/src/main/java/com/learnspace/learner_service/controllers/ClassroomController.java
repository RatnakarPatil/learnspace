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
import java.util.Map;

@RestController
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/joinClassroom")
    public ResponseEntity<String> joinClassroom(
            @RequestParam Long userId,
            @RequestParam String classroomCode) {

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            String response = classroomService.joinClassroom(userId, classroomCode);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/myClassrooms")
    public ResponseEntity<?> getClassrooms(@RequestParam Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            List<ClassroomDTO> classrooms = classroomService.getClassroomsForLearner(userId);
            return ResponseEntity.ok(classrooms);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/classrooms/{classroomId}")
    public ResponseEntity<?> getClassroom(@RequestParam Long userId, @RequestParam Long classroomId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            ClassroomDTO dto = classroomService.getSpecifiedClassroom(userId, classroomId);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/classrooms/{classroomId}/files")
    public ResponseEntity<?> getFiles(@RequestParam Long userId, @PathVariable Long classroomId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            List<FileDTO> files = classroomService.getFilesForClassroomOfLearner(userId, classroomId);
            return ResponseEntity.ok(files);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/leaveClassroom")
    public ResponseEntity<String> leaveClassroom(@RequestParam Long userId, @RequestParam Long classroomId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Learner not logged in.");
        }

        try {
            String result = classroomService.leaveClassroom(userId, classroomId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
