package com.learnspace.mentor_service.controllers;

import com.learnspace.mentor_service.dtos.FileDTO;
import com.learnspace.mentor_service.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/classroom/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Upload a file to a classroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content)
    })
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("classroomId") Long classroomId,
                                        @RequestParam("file") MultipartFile file,
                                        HttpSession session) {
        Long uploadedById = (Long) session.getAttribute("mentorUniqueId");
        if (uploadedById == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please login as mentor");
        }

        FileDTO fileDTO = fileService.uploadFile(classroomId, uploadedById, file);
        return ResponseEntity.ok(fileDTO);
    }

    @Operation(summary = "Get all files uploaded for a classroom")
    @ApiResponse(responseCode = "200", description = "Files retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileDTO.class)))
    @GetMapping("/all/{classroomId}")
    public ResponseEntity<List<FileDTO>> getAllFiles(@PathVariable Long classroomId) {
        return ResponseEntity.ok(fileService.getAllFiles(classroomId));
    }

    @Operation(summary = "Download a file by file ID")
    @ApiResponse(responseCode = "200", description = "File downloaded successfully",
            content = @Content(mediaType = "application/octet-stream"))
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        byte[] data = fileService.downloadFile(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("file").build());

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @Operation(summary = "Delete a file by ID")
    @ApiResponse(responseCode = "200", description = "File deleted successfully")
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted successfully");
    }

    @Operation(summary = "Delete all files for a classroom")
    @ApiResponse(responseCode = "200", description = "All files deleted successfully")
    @DeleteMapping("/delete-all/{classroomId}")
    public ResponseEntity<String> deleteAllFiles(@PathVariable Long classroomId) {
        fileService.deleteAllFiles(classroomId);
        return ResponseEntity.ok("All files deleted for classroom ID " + classroomId);
    }
}
