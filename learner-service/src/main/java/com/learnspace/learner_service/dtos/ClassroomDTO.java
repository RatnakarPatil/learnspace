package com.learnspace.learner_service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClassroomDTO {
    private Long classroomId;
    private String classroomName;
    private String classroomCode;
    private List<FileDTO> files; // Optional for use case
}