package com.learnspace.learner_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    private String assessmentTitle;
    private int totalMarks;
    private int passingMarks;
    private int duration;
    private Date createdAt;
    private boolean isActive;

    // Many assessments belong to one user (Learner/Mentor)
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // An assessment contains multiple questions
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
}
