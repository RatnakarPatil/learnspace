package com.learnspace.mentor_service.controllers;

import com.learnspace.mentor_service.dtos.ClassroomDTO;
import com.learnspace.mentor_service.services.ClassroomService;
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

    @PostMapping(path = "/createClassroom")
    public ResponseEntity<ClassroomDTO> createClassroom(@RequestParam String classroomName, HttpSession session) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId");
        if (mentorUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        ClassroomDTO classroomDTO = classroomService.createClassroom(mentorUniqueId, classroomName); // Use DTO
        return ResponseEntity.ok(classroomDTO);
    }

    @GetMapping("/myClassrooms")
    public ResponseEntity<List<ClassroomDTO>> getClassrooms(HttpSession session) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId"); // Retrieve from session
        if (mentorUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getMentorClassrooms(mentorUniqueId));
    }

    @GetMapping("/classrooms/{classroomId}")
    public ResponseEntity<?> getClassroom(@PathVariable Long classroomId, HttpSession session) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId"); // Retrieve from session
        if (mentorUniqueId == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please login as mentor.");
        }
        try {
            ClassroomDTO classroom = classroomService.getSpecifiedClassroom(mentorUniqueId, classroomId);
            return ResponseEntity.ok(classroom);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // or 403 for forbidden
        }
    }


    @DeleteMapping("/deleteClassroom/{classroomId}")
    public ResponseEntity<String> deleteClassroom(@PathVariable Long classroomId, HttpSession session) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId");
        if (mentorUniqueId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mentor not logged in.");
        }
        try {
            classroomService.deleteClassroom(mentorUniqueId, classroomId);
            return ResponseEntity.ok("Classroom deleted successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/updateClassroom/{classroomId}")
    public ResponseEntity<?> updateClassroom(
            @PathVariable Long classroomId,
            @RequestParam String newName,
            HttpSession session) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId");
        if (mentorUniqueId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mentor not logged in.");
        }
        try {
            ClassroomDTO updated = classroomService.updateClassroom(mentorUniqueId, classroomId, newName);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}