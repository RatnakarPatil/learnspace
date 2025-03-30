package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.pojos.Classroom;
import com.learnspace.learner_service.services.ClassroomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/join/{classroomId}")
    public ResponseEntity<String> joinClassroom(@PathVariable Long classroomId, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId"); // Retrieve from session
        if (learnerUniqueId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return ResponseEntity.ok(classroomService.joinClassroom(learnerUniqueId, classroomId));
    }

    @GetMapping("/my-classrooms")
    public ResponseEntity<List<Classroom>> getClassrooms(HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId"); // Retrieve from session
        if (learnerUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getLearnerClassrooms(learnerUniqueId));
    }

    @GetMapping("/{classroomId}")
    public ResponseEntity<Classroom> getClassroom(@PathVariable Long classroomId, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId"); // Retrieve from session
        if (learnerUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getSpecifiedClassroom(learnerUniqueId, classroomId));
    }
}
