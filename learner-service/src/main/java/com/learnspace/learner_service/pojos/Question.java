package com.learnspace.learner_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private int currentCycleDay; // 1, 3, 7, 15, 30 (next stage in cycle)
    private LocalDate nextRevisionDate; // When to show next time
    private boolean isActive = true; // false = fully learned, stop showing
}
