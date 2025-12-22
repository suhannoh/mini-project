package com.example.backend.dto.auth;

import com.example.backend.domain.User;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LoginResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
