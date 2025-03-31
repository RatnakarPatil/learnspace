package com.learnspace.mentor_service.controllers;

import com.learnspace.mentor_service.dtos.ClassroomDTO;
import com.learnspace.mentor_service.pojos.Classroom;
import com.learnspace.mentor_service.services.ClassroomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping(path = "/createClassroom")
    public ResponseEntity<ClassroomDTO> createClassroom(@RequestParam String classroomName, HttpSession session) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId"); // Retrieve from session
        if (mentorUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        ClassroomDTO classroomDTO = classroomService.createClassroom(mentorUniqueId, classroomName); // Use DTO
        return ResponseEntity.ok(classroomDTO);
    }

    @GetMapping("/my-classrooms")
    public ResponseEntity<List<Classroom>> getClassrooms(HttpSession session) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId"); // Retrieve from session
        if (mentorUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getMentorClassrooms(mentorUniqueId));
    }

    @GetMapping("/classrooms/{classroomId}")
    public ResponseEntity<Classroom> getClassroom(@PathVariable Long classroomId, HttpSession session) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId"); // Retrieve from session
        if (mentorUniqueId == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(classroomService.getSpecifiedClassroom(mentorUniqueId, classroomId));
    }
}


//@GetMapping("/my-classrooms")
//public ResponseEntity<List<ClassroomDTO>> getClassrooms(HttpSession session) {
//    Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId"); // Retrieve from session
//    if (mentorUniqueId == null) {
//        return ResponseEntity.status(401).body(null);
//    }
//    List<ClassroomDTO> listClassroomDTO = classroomService.getMentorClassrooms(mentorUniqueId); // Use DTO
//    return ResponseEntity.ok(listClassroomDTO);
//}
//
//@GetMapping("/classrooms/{classroomId}")
//public ResponseEntity<ClassroomDTO> getClassroom(@PathVariable Long classroomId, HttpSession session) {
//    Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId"); // Retrieve from session
//    if (mentorUniqueId == null) {
//        return ResponseEntity.status(401).body(null);
//    }
//    ClassroomDTO classroomDTO = classroomService.getSpecifiedClassroom(mentorUniqueId, classroomId); // Use DTO
//    return ResponseEntity.ok(classroomDTO);
//}