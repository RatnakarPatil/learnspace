package com.learnspace.auth_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String email;
    private String password;
    private Date dob;
    private String gender;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        LEARNER, MENTOR
    }
}
