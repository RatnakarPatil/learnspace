package com.learnspace.learner_service.services;

import com.learnspace.learner_service.pojos.Classroom;
import com.learnspace.learner_service.pojos.User;
import com.learnspace.learner_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    @Autowired
    private UserRepo ur;

    @Autowired
    private AssessmentRepo ar;

    @Autowired
    private ClassroomRepo cr;

    @Autowired
    private FilesRepo fr;

    @Autowired
    private QuestionsRepo qr;

    //classroom
    public String joinClassroom(Long learnerId, Long classroomId) {
        User learner = ur.findById(learnerId).orElseThrow(() -> new RuntimeException("Learner not found"));
        Classroom classroom = cr.findById(classroomId).orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (!learner.getClassrooms().contains(classroom)) {
            learner.getClassrooms().add(classroom);
            ur.save(learner);
            return "Learner has joined the classroom successfully";
        } else {
            return "Learner is already part of this classroom.";
        }
    }

    public List<Classroom> getLearnerClassrooms(Long learnerId) {
        User learner = ur.findById(learnerId).orElseThrow(() -> new RuntimeException("Learner not found"));
        return learner.getClassrooms();
    }

    public Classroom getSpecifiedClassroom(Long learnerId, Long classroomId) {
        User learner = ur.findById(learnerId).orElseThrow(() -> new RuntimeException("Learner not found"));
        Classroom classroom = cr.findById(classroomId).orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (learner.getClassrooms().contains(classroom)) {
            return classroom;
        } else {
            throw new RuntimeException("Learner is not part of this classroom");
        }
    }
}
