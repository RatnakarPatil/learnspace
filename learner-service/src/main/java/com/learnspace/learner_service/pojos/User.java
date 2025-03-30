package com.learnspace.learner_service.pojos;

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
@Table(name = "users")
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

    // Many learners can be part of many classrooms
    @ManyToMany(mappedBy = "learners")
    private List<Classroom> classrooms;

    // A user (learner/mentor) can create multiple assessments
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assessment> assessments;

    // A user (learner/mentor) can upload multiple files
    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> uploadedFiles;
}
