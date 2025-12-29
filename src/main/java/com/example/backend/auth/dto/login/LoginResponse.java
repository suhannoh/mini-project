package com.example.backend.auth.dto.login;

import com.example.backend.user.domain.Role;
import com.example.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    private long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String gender;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LoginResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.gender = user.getGender();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
