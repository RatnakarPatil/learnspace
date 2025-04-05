package com.learnspace.mentor_service.services;

import com.learnspace.mentor_service.dtos.ClassroomDTO;
import com.learnspace.mentor_service.pojos.Classroom;
import com.learnspace.mentor_service.pojos.User;
import com.learnspace.mentor_service.repository.ClassroomRepo;
import com.learnspace.mentor_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    private static final String CODE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private String generateUniqueClassroomCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                sb.append(CODE_CHARACTERS.charAt(RANDOM.nextInt(CODE_CHARACTERS.length())));
            }
            code = sb.toString();
        } while (classroomRepo.existsByClassroomCode(code)); // Ensure uniqueness
        return code;
    }

    public ClassroomDTO createClassroom(Long mentorId, String classroomName) {
        User mentor = userRepo.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        if (mentor.getRole() != User.Role.MENTOR) {
            throw new RuntimeException("Unauthorized: Mentor must be logged in.");
        }

        Classroom classroom = new Classroom();
        classroom.setClassroomName(classroomName);
        classroom.setClassroomCode(generateUniqueClassroomCode());
        classroom.setMentor(mentor);

        Classroom savedClassroom = classroomRepo.save(classroom);
        return new ClassroomDTO(savedClassroom);
    }

    public List<ClassroomDTO> getMentorClassrooms(Long mentorId) {
        return classroomRepo.findByMentorUserId(mentorId)
                .stream()
                .map(classroom -> {
                    ClassroomDTO dto = new ClassroomDTO(classroom);
                    List<String> learnerEmails = userRepo.findLearnersEmailsByClassroomId(classroom.getClassroomId());
                    dto.setLearnerEmails(learnerEmails);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ClassroomDTO getSpecifiedClassroom(Long mentorId, Long classroomId) {
        User mentor = userRepo.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        Classroom classroom = classroomRepo.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (!mentor.getCreatedClassrooms().contains(classroom)) {
            throw new RuntimeException("Mentor is not part of this classroom");
        }

        ClassroomDTO dto = new ClassroomDTO(classroom);
        List<String> learnerEmails = userRepo.findLearnersEmailsByClassroomId(classroomId);
        dto.setLearnerEmails(learnerEmails);
        return dto;
    }
}