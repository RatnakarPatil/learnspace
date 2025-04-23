package com.learnspace.mentor_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AssessmentRequestDTO {
    private String title;
    private Long classroomId;
    private List<QuestionDTO> mcqQuestions;
}