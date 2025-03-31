package com.learnspace.mentor_service.dtos;

import com.learnspace.mentor_service.pojos.Classroom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassroomDTO {
    private Long classroomId;
    private String classroomName;
    private String classroomCode;
    private UserDTO mentor; // Use another DTO to limit mentor details

    public ClassroomDTO(Classroom classroom) {
        this.classroomId = classroom.getClassroomId();
        this.classroomName = classroom.getClassroomName();
        this.classroomCode = classroom.getClassroomCode();
        this.mentor = new UserDTO(classroom.getMentor()); // Convert mentor entity to DTO
    }
}
