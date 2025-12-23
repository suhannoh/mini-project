package com.example.backend.post_like.dto;

public record LikeCreateRequest(
        Long postId, Long userId
) {
}
