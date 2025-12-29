package com.example.backend.user.dto.find;

import jakarta.validation.constraints.NotBlank;

public record UserFindRequest(
        @NotBlank (message = "이메일이 비어있습니다")
        String email ,
        @NotBlank (message = "이름이 비어있습니다")
        String name
) {
}
