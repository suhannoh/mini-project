package com.example.backend.admin.dto;

import com.example.backend.user.domain.Role;
import com.example.backend.user.domain.Status;
import com.example.backend.user.domain.User;

import java.time.LocalDateTime;

public record FindUsersResponse(
        Long id ,
        String email,
//        String password,
        String name,
        String phone,
        String gender,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Status status
) {
    public static FindUsersResponse create (User user) {
        return new FindUsersResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getGender(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getStatus()
        );
    }
}
