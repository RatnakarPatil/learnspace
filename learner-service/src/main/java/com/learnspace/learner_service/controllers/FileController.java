package com.learnspace.learner_service.controllers;

import com.learnspace.learner_service.dtos.FileDTO;
import com.learnspace.learner_service.dtos.FileDownloadDTO;
import com.learnspace.learner_service.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @Operation(summary = "Get all files in a classroom")
    @ApiResponse(responseCode = "200", description = "Files fetched successfully")
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<FileDTO>> getFilesInClassroom(@PathVariable Long classroomId) {
        return ResponseEntity.ok(fileService.getFilesForClassroom(classroomId));
    }

    @Operation(summary = "Download a file")
    @ApiResponse(responseCode = "200", description = "File downloaded successfully")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        FileDownloadDTO fileDownload = fileService.downloadFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDownload.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(fileDownload.getContentType()))
                .body(fileDownload.getResource());
    }

    @Operation(summary = "Get file metadata")
    @ApiResponse(responseCode = "200", description = "File metadata fetched successfully")
    @GetMapping("/metadata/{fileId}")
    public ResponseEntity<FileDTO> getFileMetadata(@PathVariable Long fileId) {
        return ResponseEntity.ok(fileService.getFileMetadata(fileId));
    }
}
