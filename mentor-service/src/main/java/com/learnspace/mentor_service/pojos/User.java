package com.learnspace.mentor_service.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users") // Shared table between Learner & Mentor Services
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String email;
    private String password;
    private Date dob;
    private String gender;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;  // LEARNER or MENTOR

    public enum Role {
        LEARNER, MENTOR
    }

    // Mentor-specific relationships
    @JsonIgnore
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Classroom> classrooms;  // Mentor can create multiple classrooms

    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL)
    private List<File> uploadedFiles; // Mentor uploads files

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)  // Fixed mapping
    private List<Assessment> createdAssessments; // Mentor creates assessments
}


//    // Learner-specific relationships
//    @ManyToMany(mappedBy = "learners", cascade = CascadeType.ALL)
//    private List<Classroom> joinedClassrooms; // Learner can join multiple classrooms
//
//    @OneToMany(mappedBy = "learner", cascade = CascadeType.ALL)
//    private List<Assessment> attemptedAssessments; // Learner attempts assessments