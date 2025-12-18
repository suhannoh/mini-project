package com.example.backend.dto.comments;


public record CommentRequestDto(
        Long postId,
        Long userId,
        String comment
) {
}
