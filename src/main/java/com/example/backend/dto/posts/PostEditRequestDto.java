package com.example.backend.dto.posts;

public record PostEditRequestDto(
        Long postId,
        Long userId,
        String title,
        String content,
        String category,
        String author
) {
}
