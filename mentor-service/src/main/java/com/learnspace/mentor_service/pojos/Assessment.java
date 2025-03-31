package com.learnspace.mentor_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private User createdBy; // ✅ Created by a mentor

    // An assessment contains multiple questions
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
}


//    @ManyToOne
//    @JoinColumn(name = "learner_id", referencedColumnName = "userId", nullable = true)
//    private User learner;
