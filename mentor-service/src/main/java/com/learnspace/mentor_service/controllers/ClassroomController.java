package com.learnspace.mentor_service.controllers;

import com.learnspace.mentor_service.dtos.ClassroomDTO;
import com.learnspace.mentor_service.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping(path = "/createClassroom")
    public ResponseEntity<ClassroomDTO> createClassroom(@RequestParam Long userId, @RequestParam String classroomName) {
        if (userId == null) {
            return ResponseEntity.status(401).body(null);
        }
        ClassroomDTO classroomDTO = classroomService.createClassroom(userId, classroomName); // Use DTO
        return ResponseEntity.ok(classroomDTO);
    }

    @GetMapping("/myClassrooms")
    public ResponseEntity<List<ClassroomDTO>> getClassrooms(@RequestParam Long userId) {
        if (userId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getMentorClassrooms(userId));
    }

    @GetMapping("/classrooms/{classroomId}")
    public ResponseEntity<?> getClassroom(@RequestParam Long userId, @PathVariable Long classroomId) {
        if (userId == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please login as mentor.");
        }
        try {
            ClassroomDTO classroom = classroomService.getSpecifiedClassroom(userId, classroomId);
            return ResponseEntity.ok(classroom);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // or 403 for forbidden
        }
    }

    @DeleteMapping("/deleteClassroom/{classroomId}")
    public ResponseEntity<String> deleteClassroom(@RequestParam Long userId, @RequestParam Long classroomId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mentor not logged in.");
        }
        try {
            classroomService.deleteClassroom(userId, classroomId);
            return ResponseEntity.ok("Classroom deleted successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/updateClassroom/{classroomId}")
    public ResponseEntity<?> updateClassroom(
            @RequestParam Long userId,
            @RequestParam Long classroomId,
            @RequestParam String newName) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mentor not logged in.");
        }
        try {
            ClassroomDTO updated = classroomService.updateClassroom(userId, classroomId, newName);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}