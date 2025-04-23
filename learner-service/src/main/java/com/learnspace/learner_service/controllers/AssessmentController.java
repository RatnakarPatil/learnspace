package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.dtos.SubmitAssessmentDTO;
import com.learnspace.learner_service.pojos.Assessment;
import com.learnspace.learner_service.services.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @Operation(
            summary = "Submit an assessment",
            description = "Learner submits answers for an assessment. Returns updated assessment with obtained marks."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assessment submitted successfully"),
            @ApiResponse(responseCode = "404", description = "Assessment or Learner not found")
    })
    @PostMapping("/submit")
    public ResponseEntity<?> submit(
            @RequestBody SubmitAssessmentDTO dto
    ) {
        return ResponseEntity.ok(assessmentService.submitAssessment(dto));
    }

    @Operation(
            summary = "Get all assessments submitted by learner",
            description = "Fetches all assessments attempted or created."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assessments fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Learner not found")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Assessment>> getAll(
            HttpSession session
    ) {
        Long learnerUniqueId = (Long) session.getAttribute("learnerUniqueId");
        return ResponseEntity.ok(assessmentService.getAssessmentsForLearner(learnerUniqueId));
    }
}
