package com.learnspace.mentor_service.dtos;

import com.learnspace.mentor_service.pojos.Classroom;
import com.learnspace.mentor_service.pojos.File;
import com.learnspace.mentor_service.pojos.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClassroomDTO {
    private Long classroomId;
    private String classroomName;
    private String classroomCode;
    private List<String> fileNames;
    private List<String> learnerEmails;

    public ClassroomDTO(Classroom classroom) {
        this.classroomId = classroom.getClassroomId();
        this.classroomName = classroom.getClassroomName();
        this.classroomCode = classroom.getClassroomCode();
        this.fileNames = classroom.getFiles().stream()
                .map(File::getFileName)
                .collect(Collectors.toList());
    }
}
