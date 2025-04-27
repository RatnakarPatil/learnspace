package com.learnspace.mentor_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDTO {
    private Long assessmentId;
    private String assessmentTitle;
    private Date createdAt;
}
