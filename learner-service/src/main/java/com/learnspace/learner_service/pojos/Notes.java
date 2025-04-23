package com.learnspace.learner_service.pojos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "learner_id", nullable = false)
    private User learner;

    @OneToMany(mappedBy = "notes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubNotes> subNotes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

