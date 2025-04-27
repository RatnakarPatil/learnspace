package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.dtos.NotesDTO;
import com.learnspace.learner_service.services.NotesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
@Tag(name = "Notes API", description = "APIs for managing AI-generated notes for learners")
public class NotesController {

    private final NotesService notesService;

    @Operation(summary = "Save new note", description = "Save a new note with a list of sub-notes for a learner")
    @ApiResponse(responseCode = "200", description = "Note created successfully")
    @PostMapping
    public ResponseEntity<NotesDTO> saveNote(@RequestBody NotesDTO dto, HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");
        return ResponseEntity.ok(notesService.saveNote(dto, learnerUniqueId));
    }

    @Operation(summary = "Update an existing note", description = "Updates the note and its sub-notes by ID")
    @ApiResponse(responseCode = "200", description = "Note updated successfully")
    @PutMapping("/{noteId}")
    public ResponseEntity<NotesDTO> updateNote(@PathVariable Long noteId, @RequestBody NotesDTO dto) {
        return ResponseEntity.ok(notesService.updateNote(noteId, dto));
    }

    @Operation(summary = "Delete a note", description = "Deletes a note and its sub-notes by ID")
    @ApiResponse(responseCode = "204", description = "Note deleted successfully")
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        notesService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a note by ID", description = "Retrieves a note and its sub-notes by note ID")
    @ApiResponse(responseCode = "200", description = "Note retrieved successfully")
    @GetMapping("/{noteId}")
    public ResponseEntity<NotesDTO> getNoteById(@PathVariable Long noteId) {
        return ResponseEntity.ok(notesService.getNoteById(noteId));
    }

    @Operation(summary = "Get all notes by learner", description = "Retrieves all notes created by a learner")
    @ApiResponse(responseCode = "200", description = "Notes retrieved successfully")
    @GetMapping("/learnerNotes")
    public ResponseEntity<List<NotesDTO>> getNotesByLearner(HttpSession session) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");
        return ResponseEntity.ok(notesService.getAllNotesByLearner(learnerUniqueId));
    }
}


