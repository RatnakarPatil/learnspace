package com.learnspace.learner_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
}