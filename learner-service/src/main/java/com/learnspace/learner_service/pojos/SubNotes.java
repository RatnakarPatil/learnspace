package com.learnspace.learner_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subNoteId;

    private String subTopic;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @ManyToOne
    @JoinColumn(name = "notes_id")
    private Notes notes;
}

