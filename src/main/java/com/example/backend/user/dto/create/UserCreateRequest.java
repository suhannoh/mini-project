package com.example.backend.user.dto.create;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserCreateRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
