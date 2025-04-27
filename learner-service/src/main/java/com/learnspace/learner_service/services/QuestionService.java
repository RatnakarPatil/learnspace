package com.learnspace.learner_service.services;

import com.learnspace.learner_service.dtos.QuestionDTO;
import com.learnspace.learner_service.pojos.Question;
import com.learnspace.learner_service.pojos.User;
import com.learnspace.learner_service.repository.QuestionRepo;
import com.learnspace.learner_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private UserRepo userRepo;

    // 1. Save Questions (Bulk from Frontend)
    public void saveQuestions(List<QuestionDTO> dtos, Long createdBy) {
        List<Question> questions = dtos.stream().map(dto -> {
            Question q = new Question();
            q.setQuestionText(dto.getQuestionText());
            q.setOption1(dto.getOption1());
            q.setOption2(dto.getOption2());
            q.setOption3(dto.getOption3());
            q.setOption4(dto.getOption4());
            q.setCorrectOption(dto.getCorrectOption());

            User user = userRepo.findById(createdBy).orElseThrow();
            q.setCreatedBy(user);
            q.setCurrentCycleDay(1);
            q.setNextRevisionDate(LocalDate.now());
            q.setActive(true);

            return q;
        }).collect(Collectors.toList());

        questionRepo.saveAll(questions);
    }

    // 2. Get questions due today
    public List<QuestionDTO> getDueQuestions(Long learnerId) {
        List<Question> questions = questionRepo.findByCreatedByUserIdAndNextRevisionDateAndIsActive(
                learnerId, LocalDate.now(), true);

        return questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // 3. Record an attempt and update revision
    public void recordAttempt(Long questionId, boolean isCorrect) {
        Question q = questionRepo.findById(questionId).orElseThrow();

        if (!isCorrect) {
            q.setCurrentCycleDay(1);
            q.setNextRevisionDate(LocalDate.now().plusDays(1));
        } else {
            int nextDay = getNextCycleDay(q.getCurrentCycleDay());
            q.setCurrentCycleDay(nextDay);
            q.setNextRevisionDate(LocalDate.now().plusDays(nextDay));

            if (nextDay == 30) {
                q.setActive(false); // Fully learned
            }
        }

        questionRepo.save(q);
    }

    private int getNextCycleDay(int current) {
        return switch (current) {
            case 1 -> 3;
            case 3 -> 7;
            case 7 -> 15;
            case 15 -> 30;
            default -> 30;
        };
    }

    // 4. List all active/inactive questions
    public List<QuestionDTO> listAllQuestions(Long learnerId) {
        List<Question> questions = questionRepo.findByCreatedByUserId(learnerId);
        return questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private QuestionDTO convertToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionText(question.getQuestionText());
        dto.setOption1(question.getOption1());
        dto.setOption2(question.getOption2());
        dto.setOption3(question.getOption3());
        dto.setOption4(question.getOption4());
        dto.setCorrectOption(question.getCorrectOption());
        return dto;
    }

    // 5. Delete question
    public void deleteQuestion(Long id) {
        questionRepo.deleteById(id);
    }
}
