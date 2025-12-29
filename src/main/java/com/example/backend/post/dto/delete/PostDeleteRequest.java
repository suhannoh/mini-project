package com.example.backend.post.dto.delete;

import jakarta.validation.constraints.NotNull;

public record PostDeleteRequest(
        @NotNull(message = "삭제하려는 USER_ID가 비어있습니다")
        Long userId
) {
}
