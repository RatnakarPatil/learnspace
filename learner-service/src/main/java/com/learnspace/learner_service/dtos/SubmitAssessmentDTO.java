package com.learnspace.learner_service.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class SubmitAssessmentDTO {
    private Long assessmentId;
    private Map<Long, String> selectedAnswers; // questionId -> selectedOption
    private Long learnerId;
}
