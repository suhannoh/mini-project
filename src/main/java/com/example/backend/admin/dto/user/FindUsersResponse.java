package com.example.backend.admin.dto.user;

import com.example.backend.admin.domain.UserBlockHistory;
import com.example.backend.user.domain.Role;
import com.example.backend.user.domain.Status;
import com.example.backend.user.domain.User;

import java.time.LocalDateTime;

public record FindUsersResponse(
        Long id ,
        String email,
        String name,
        String phone,
        String gender,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLoginAt,
        Status status,
        String reason,
        LocalDateTime blockedAt,
        long blockCount
) {
}
