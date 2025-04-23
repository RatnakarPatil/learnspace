package com.learnspace.mentor_service.controllers;

import com.learnspace.mentor_service.dtos.AssessmentRequestDTO;
import com.learnspace.mentor_service.pojos.Assessment;
import com.learnspace.mentor_service.services.AssessmentService;
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
            summary = "Generate an AI-powered assessment",
            description = "Generates a new assessment based on input topic or content using AI. The mentor must be logged in (session-based)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assessment generated successfully"),
            @ApiResponse(responseCode = "401", description = "Mentor session not found or expired"),
            @ApiResponse(responseCode = "400", description = "Invalid assessment request data")
    })
    @PostMapping("/generate")
    public ResponseEntity<?> generateAssessment(
            @RequestBody AssessmentRequestDTO dto,
            HttpSession session
    ) {
        Long mentorUniqueId = (Long) session.getAttribute("mentorUniqueId");
        return ResponseEntity.ok(assessmentService.createAssessmentFromAI(dto, mentorUniqueId));
    }

    @Operation(
            summary = "Get assessments for a classroom",
            description = "Fetches all assessments associated with the specified classroom ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assessments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Classroom not found")
    })
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<Assessment>> getClassroomAssessments(
            @Parameter(description = "ID of the classroom", required = true)
            @PathVariable Long classroomId
    ) {
        return ResponseEntity.ok(assessmentService.getAssessmentsForClassroom(classroomId));
    }
}

