package com.learnspace.learner_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class QuestionListDTO {
    private List<QuestionDTO> mcqQuestions;
}