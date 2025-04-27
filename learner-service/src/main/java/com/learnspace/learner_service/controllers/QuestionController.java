package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.dtos.QuestionDTO;
import com.learnspace.learner_service.dtos.QuestionListDTO;
import com.learnspace.learner_service.pojos.Question;
import com.learnspace.learner_service.services.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "Save multiple MCQs", description = "Accepts a list of MCQs and saves them for the logged-in learner.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questions saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping("/saveBulk")
    public ResponseEntity<?> saveBulk(
            @RequestParam Long userId,
            @RequestBody QuestionListDTO request) {
        questionService.saveQuestions(request.getMcqQuestions(), userId);
        return ResponseEntity.ok("Questions saved successfully.");
    }

    @Operation(summary = "Record question attempt", description = "Updates question status based on whether the answer was correct.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attempt recorded"),
            @ApiResponse(responseCode = "404", description = "Question not found", content = @Content)
    })
    @PostMapping("/attempt")
    public ResponseEntity<?> record(
            @Parameter(description = "Question ID") @RequestParam Long questionId,
            @Parameter(description = "Was the answer correct?") @RequestParam boolean isCorrect) {
        questionService.recordAttempt(questionId, isCorrect);
        return ResponseEntity.ok("Attempt recorded.");
    }

    @CrossOrigin(origins = "*")
    @Operation(summary = "Get today's revision questions", description = "Fetches all questions due for revision today.")
    @ApiResponse(responseCode = "200", description = "List of due questions")
    @GetMapping("/dueToday")
    public ResponseEntity<List<QuestionDTO>> getToday(@RequestParam Long userId) {
        return ResponseEntity.ok(questionService.getDueQuestions(userId));
    }

    @Operation(summary = "Get all questions", description = "Returns all questions (active and inactive).")
    @ApiResponse(responseCode = "200", description = "All questions fetched")
    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAll(@RequestParam Long userId) {
        return ResponseEntity.ok(questionService.listAllQuestions(userId));
    }

    @Operation(summary = "Delete a question", description = "Deletes a question by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Question not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok("Deleted successfully.");
    }
}
