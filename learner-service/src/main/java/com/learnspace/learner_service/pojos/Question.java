package com.learnspace.learner_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctOption;
    private int marks;
    private String createdBy;
    private Date createdAt;
    private String questionDifficulty;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;  // Question belongs to an assessment
}
