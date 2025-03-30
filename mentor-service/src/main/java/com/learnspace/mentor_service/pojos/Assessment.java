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

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy; // ✅ Created by a mentor

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private User learner; // ✅ Attempted by a learner

    @OneToMany(mappedBy = "assessment")
    private List<Question> questions;
}
