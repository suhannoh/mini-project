package com.example.backend.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JoinUserRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
