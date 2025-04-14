package com.learnspace.mentor_service.services;

import com.learnspace.mentor_service.dtos.FileDTO;
import com.learnspace.mentor_service.pojos.Classroom;
import com.learnspace.mentor_service.pojos.File;
import com.learnspace.mentor_service.pojos.User;
import com.learnspace.mentor_service.repository.ClassroomRepo;
import com.learnspace.mentor_service.repository.FileRepo;
import com.learnspace.mentor_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    @Autowired
    private UserRepo userRepo;

    public FileDTO uploadFile(Long classroomId, Long uploadedById, MultipartFile file) {
        Classroom classroom = classroomRepo.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        User uploader = userRepo.findById(uploadedById)
                .orElseThrow(() -> new RuntimeException("Uploader not found"));

        File fileEntity = new File();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileData(getBytes(file));
        fileEntity.setClassroom(classroom);
        fileEntity.setUploadedBy(uploader);

        File saved = fileRepo.save(fileEntity);

        return new FileDTO(saved.getFileId(), saved.getFileName(), saved.getFileType(),
                saved.getClassroom().getClassroomId(), saved.getUploadedBy().getUserId());
    }

    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file data", e);
        }
    }

    public List<FileDTO> getAllFiles(Long classroomId) {
        List<File> files = fileRepo.findByClassroomClassroomId(classroomId);
        return files.stream()
                .map(f -> new FileDTO(
                        f.getFileId(), f.getFileName(), f.getFileType(),
                        f.getClassroom().getClassroomId(),
                        f.getUploadedBy().getUserId()))
                .collect(Collectors.toList());
    }

    public byte[] downloadFile(Long fileId) {
        File file = fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        return file.getFileData();
    }

    public void deleteFile(Long fileId) {
        if (!fileRepo.existsById(fileId)) {
            throw new RuntimeException("File not found");
        }
        fileRepo.deleteById(fileId);
    }

    public void deleteAllFiles(Long classroomId) {
        List<File> files = fileRepo.findByClassroomClassroomId(classroomId);
        fileRepo.deleteAll(files);
    }
}
