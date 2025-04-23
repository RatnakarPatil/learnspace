package com.learnspace.mentor_service.services;

import com.learnspace.mentor_service.dtos.AssessmentRequestDTO;
import com.learnspace.mentor_service.pojos.Assessment;
import com.learnspace.mentor_service.pojos.Question;
import com.learnspace.mentor_service.pojos.User;
import com.learnspace.mentor_service.repository.AssessmentRepo;
import com.learnspace.mentor_service.repository.ClassroomRepo;
import com.learnspace.mentor_service.repository.QuestionRepo;
import com.learnspace.mentor_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepo assessmentRepo;
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private ClassroomRepo classroomRepo;
    @Autowired
    private UserRepo userRepo;

    public Assessment createAssessmentFromAI(AssessmentRequestDTO dto, Long mentorId) {
        User mentor = userRepo.findById(mentorId).orElseThrow();

        Assessment assessment = new Assessment();
        assessment.setAssessmentTitle(dto.getTitle());
        assessment.setCreatedAt(new Date());
        assessment.setCreatedBy(mentor);
        assessment.setActive(true);

        List<Question> questions = dto.getMcqQuestions().stream().map(q -> {
            Question question = new Question();
            question.setQuestionText(q.getQuestionText());
            question.setOption1(q.getOption1());
            question.setOption2(q.getOption2());
            question.setOption3(q.getOption3());
            question.setOption4(q.getOption4());
            question.setCorrectOption(q.getCorrectOption());
            question.setAssessment(assessment);
            return question;
        }).collect(Collectors.toList());

        assessment.setQuestions(questions);
        assessment.setTotalMarks(questions.size());
        return assessmentRepo.save(assessment);
    }

    public List<Assessment> getAssessmentsForClassroom(Long classroomId) {
        return classroomRepo.findByClassroomId(classroomId); // You can add this custom query
    }
}
