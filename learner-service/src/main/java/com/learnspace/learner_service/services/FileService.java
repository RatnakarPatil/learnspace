package com.learnspace.learner_service.services;

import com.learnspace.learner_service.dtos.FileDTO;
import com.learnspace.learner_service.pojos.Classroom;
import com.learnspace.learner_service.pojos.File;
import com.learnspace.learner_service.repository.ClassroomRepo;
import com.learnspace.learner_service.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileService {
    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    public List<FileDTO> getFilesForClassroom(Long classroomId) {
        Optional<Classroom> classroomOpt = classroomRepo.findById(classroomId);
        if (classroomOpt.isEmpty()) {
            throw new RuntimeException("Classroom not found");
        }

        return fileRepo.findByClassroom(classroomOpt.get())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FileDTO downloadFile(Long fileId) {
        File file = fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        return convertToDTO(file);
    }

    public FileDTO getFileMetadata(Long fileId) {
        File file = fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        FileDTO dto = new FileDTO();
        dto.setFileId(file.getFileId());
        dto.setFileName(file.getFileName());
        dto.setFileType(file.getFileType());
        return dto;
    }

    public FileDTO previewFile(Long fileId) {
        return downloadFile(fileId);
    }

    private FileDTO convertToDTO(File file) {
        FileDTO dto = new FileDTO();
        dto.setFileId(file.getFileId());
        dto.setFileName(file.getFileName());
        dto.setFileType(file.getFileType());
        dto.setFileData(file.getFileData());
        return dto;
    }
}
