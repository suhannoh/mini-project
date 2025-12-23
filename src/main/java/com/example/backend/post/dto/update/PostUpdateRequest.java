package com.example.backend.post.dto.update;

public record PostUpdateRequest(
        Long postId,
        Long userId,
        String title,
        String content,
        String category,
        String author
) {
}
