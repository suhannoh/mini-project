package com.example.backend.admin.dto;

import com.example.backend.user.domain.Role;
import com.example.backend.user.domain.Status;

public record UpdateStatusRequest(
        Long userId,
        Long adminId,
        Role role,
        Status status,
        String reason
) {
}
