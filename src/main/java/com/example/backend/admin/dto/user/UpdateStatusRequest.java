package com.example.backend.admin.dto.user;

import com.example.backend.user.domain.Role;
import com.example.backend.user.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateStatusRequest(
        Long userId,
        @NotNull(message = "어드민 ID는 필수입니다")
        Long adminId,
        Role role,
        Status status,
        String reason
) {
}
