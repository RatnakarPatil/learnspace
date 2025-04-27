package com.learnspace.learner_service.dtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesDTO {
    private Long noteId;
    private String title;
    private List<SubNotesDTO> subNotes;
}