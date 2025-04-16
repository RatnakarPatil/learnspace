package com.learnspace.learner_service.services;

import com.learnspace.learner_service.dtos.FileDTO;
import com.learnspace.learner_service.dtos.FileDownloadDTO;
import com.learnspace.learner_service.pojos.File;
import com.learnspace.learner_service.repository.ClassroomRepo;
import com.learnspace.learner_service.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    public List<FileDTO> getFilesForClassroom(Long classroomId) {
        return fileRepo.findByClassroomClassroomId(classroomId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public FileDownloadDTO downloadFile(Long fileId) {
        File file = fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Resource resource = new ByteArrayResource(file.getFileData());

        return new FileDownloadDTO(file.getFileName(), file.getFileType(), resource);
    }

    public FileDTO getFileMetadata(Long fileId) {
        File file = fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        return mapToDTO(file);
    }

    private FileDTO mapToDTO(File file) {
        FileDTO dto = new FileDTO();
        dto.setFileId(file.getFileId());
        dto.setFileName(file.getFileName());
        dto.setFileType(file.getFileType());
        dto.setClassroomId(file.getClassroom().getClassroomId());
        return dto;
    }
}
