package com.learnspace.mentor_service.dtos;

import com.learnspace.mentor_service.pojos.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String name;
    private String email;

    public UserDTO(User mentor) {
        this.userId = mentor.getUserId();
        this.name = mentor.getName();
        this.email = mentor.getEmail();
    }
}
