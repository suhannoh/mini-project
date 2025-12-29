package com.example.backend.post_like.dto;

import jakarta.validation.constraints.NotNull;

public record LikeCreateRequest(
        @NotNull(message = "POST ID가 비어있습니다. 프론트에서 확인해주세요 ")
        Long postId,
        @NotNull(message = "USER ID가 비어있습니다. 로그인을 다시 해주세요 ")
        Long userId
) {
}
