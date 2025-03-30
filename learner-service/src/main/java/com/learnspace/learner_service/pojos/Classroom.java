package com.learnspace.learner_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;

    private String classroomName;
    private String classroomCode;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private User mentor;  // Only Mentors can create classrooms

    @ManyToMany
    @JoinTable(
            name = "classroom_learners",
            joinColumns = @JoinColumn(name = "classroom_id"),
            inverseJoinColumns = @JoinColumn(name = "learner_id")
    )
    private List<User> learners;  // Learners who have joined the classroom

    @OneToMany(mappedBy = "classroom")
    private List<File> files;  // Files associated with this classroom
}
