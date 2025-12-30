package com.example.backend.post_comment.dto.read;

import com.example.backend.post_comment.domain.Comment;

import java.time.LocalDateTime;

public record readCommentResponse (
        Long id,
        Long userId,
        String name,
        String comment,
        LocalDateTime createdAt
) {
}