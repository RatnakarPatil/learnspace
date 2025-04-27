package com.learnspace.learner_service.services;

import com.learnspace.learner_service.dtos.NotesDTO;
import com.learnspace.learner_service.dtos.SubNotesDTO;
import com.learnspace.learner_service.pojos.Notes;
import com.learnspace.learner_service.pojos.SubNotes;
import com.learnspace.learner_service.pojos.User;
import com.learnspace.learner_service.repository.NotesRepo;
import com.learnspace.learner_service.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotesService {

    private final NotesRepo notesRepo;
    private final UserRepo userRepo;

    public NotesDTO saveNote(NotesDTO dto, Long learnerId) {
        User learner = userRepo.findById(learnerId)
                .orElseThrow(() -> new RuntimeException("Learner not found"));

        Notes notes = Notes.builder()
                .title(dto.getTitle())
                .learner(learner)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<SubNotes> subNotes = dto.getSubNotes().stream()
                .map(sn -> SubNotes.builder()
                        .subTopic(sn.getSubTopic())
                        .summary(sn.getSummary())
                        .notes(notes)
                        .build())
                .collect(Collectors.toList());

        notes.setSubNotes(subNotes);

        Notes saved = notesRepo.save(notes);
        return convertToDto(saved);
    }

    public NotesDTO updateNote(Long id, NotesDTO dto) {
        Notes existing = notesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        existing.setTitle(dto.getTitle());
        existing.setUpdatedAt(LocalDateTime.now());

        existing.getSubNotes().clear();
        List<SubNotes> updatedSubNotes = dto.getSubNotes().stream()
                .map(sn -> SubNotes.builder()
                        .subTopic(sn.getSubTopic())
                        .summary(sn.getSummary())
                        .notes(existing)
                        .build())
                .collect(Collectors.toList());

        existing.getSubNotes().addAll(updatedSubNotes);

        Notes saved = notesRepo.save(existing);
        return convertToDto(saved);
    }

    public void deleteNote(Long id) {
        notesRepo.deleteById(id);
    }

    public NotesDTO getNoteById(Long id) {
        Notes notes = notesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return convertToDto(notes);
    }

    public List<NotesDTO> getAllNotesByLearner(Long learnerId) {
        return notesRepo.findAllByLearner_UserId(learnerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NotesDTO convertToDto(Notes notes) {
        return NotesDTO.builder()
                .noteId(notes.getNoteId())
                .title(notes.getTitle())
                .subNotes(notes.getSubNotes().stream()
                        .map(sn -> new SubNotesDTO(sn.getSubTopic(), sn.getSummary()))
                        .collect(Collectors.toList()))
                .build();
    }
}


