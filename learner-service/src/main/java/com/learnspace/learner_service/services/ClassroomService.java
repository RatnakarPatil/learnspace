package com.learnspace.learner_service.services;

import com.learnspace.learner_service.dtos.ClassroomDTO;
import com.learnspace.learner_service.dtos.FileDTO;
import com.learnspace.learner_service.pojos.Classroom;
import com.learnspace.learner_service.pojos.User;
import com.learnspace.learner_service.repository.ClassroomRepo;
import com.learnspace.learner_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    //classroom
    public String joinClassroom(Long learnerId, String classroomCode) {
        User learner = userRepo.findById(learnerId).orElseThrow(() -> new RuntimeException("Learner not found"));
        Classroom classroom = classroomRepo.findByClassroomCode(classroomCode);

        if (!learner.getClassrooms().contains(classroom)) {
            learner.getClassrooms().add(classroom);
            userRepo.save(learner);
            return "Learner has joined the classroom successfully";
        } else {
            return "Learner is already part of this classroom.";
        }
    }

    public List<ClassroomDTO> getClassroomsForLearner(Long learnerId) {
        User learner = userRepo.findById(learnerId)
                .orElseThrow(() -> new RuntimeException("Learner not found"));

        return learner.getClassrooms().stream().map(classroom -> {
            ClassroomDTO dto = new ClassroomDTO();
            dto.setClassroomId(classroom.getClassroomId());
            dto.setClassroomName(classroom.getClassroomName());
            dto.setClassroomCode(classroom.getClassroomCode());

            // Optional: include files
            List<FileDTO> fileDTOs = classroom.getFiles().stream().map(file -> {
                FileDTO f = new FileDTO();
                f.setFileId(file.getFileId());
                f.setFileName(file.getFileName());
                return f;
            }).collect(Collectors.toList());
            dto.setFiles(fileDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

    public ClassroomDTO getSpecifiedClassroom(Long learnerId, Long classroomId) {
        User learner = userRepo.findById(learnerId)
                .orElseThrow(() -> new RuntimeException("Learner not found"));

        Classroom classroom = classroomRepo.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        // Check if learner has joined the classroom
        boolean isJoined = learner.getClassrooms()
                .stream()
                .anyMatch(c -> c.getClassroomId().equals(classroomId));

        if (!isJoined) {
            throw new RuntimeException("Learner is not part of this classroom");
        }

        // Convert to DTO
        ClassroomDTO dto = new ClassroomDTO();
        dto.setClassroomId(classroom.getClassroomId());
        dto.setClassroomName(classroom.getClassroomName());
        dto.setClassroomCode(classroom.getClassroomCode());

        // Map files to FileDTO
        if (classroom.getFiles() != null) {
            List<FileDTO> fileDTOs = classroom.getFiles().stream().map(file -> {
                FileDTO f = new FileDTO();
                f.setFileId(file.getFileId());
                f.setFileName(file.getFileName());
                return f;
            }).toList();
            dto.setFiles(fileDTOs);
        }

        return dto;
    }


    public List<FileDTO> getFilesForClassroomOfLearner(Long learnerId, Long classroomId) {
        User learner = userRepo.findById(learnerId)
                .orElseThrow(() -> new RuntimeException("Learner not found"));

        boolean joined = learner.getClassrooms().stream()
                .anyMatch(c -> c.getClassroomId().equals(classroomId));

        if (!joined) {
            throw new RuntimeException("Learner has not joined this classroom");
        }

        Classroom classroom = classroomRepo.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        return classroom.getFiles().stream().map(file -> {
            FileDTO f = new FileDTO();
            f.setFileId(file.getFileId());
            f.setFileName(file.getFileName());
            return f;
        }).collect(Collectors.toList());
    }

    public String leaveClassroom(Long learnerId, Long classroomId) {
        User learner = userRepo.findById(learnerId)
                .orElseThrow(() -> new RuntimeException("Learner not found"));

        Classroom classroom = classroomRepo.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (!learner.getClassrooms().contains(classroom)) {
            throw new RuntimeException("Learner is not part of this classroom.");
        }

        learner.getClassrooms().remove(classroom);
        classroom.getLearners().remove(learner);
        userRepo.save(learner);

        return "Successfully left the classroom.";
    }


}
