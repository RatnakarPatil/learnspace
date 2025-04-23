package com.learnspace.learner_service.services;

import com.learnspace.learner_service.dtos.SubmitAssessmentDTO;
import com.learnspace.learner_service.pojos.Assessment;
import com.learnspace.learner_service.pojos.Question;
import com.learnspace.learner_service.pojos.User;
import com.learnspace.learner_service.repository.AssessmentRepo;
import com.learnspace.learner_service.repository.QuestionRepo;
import com.learnspace.learner_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepo assessmentRepo;
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private UserRepo userRepo;

    public Assessment submitAssessment(SubmitAssessmentDTO dto) {
        User learner = userRepo.findById(dto.getLearnerId()).orElseThrow();
        Assessment assessment = assessmentRepo.findById(dto.getAssessmentId()).orElseThrow();

        int obtained = 0;
        for (Question q : assessment.getQuestions()) {
            String answer = dto.getSelectedAnswers().get(q.getQuestionId());
            if (q.getCorrectOption().equalsIgnoreCase(answer)) obtained++;
        }

        assessment.setObtainedMarks(obtained);
        assessment.setCreatedBy(learner);
        return assessmentRepo.save(assessment);
    }

    public List<Assessment> getAssessmentsForLearner(Long learnerId) {
        return assessmentRepo.findByCreatedByUserId(learnerId); // Optional custom query
    }
}
