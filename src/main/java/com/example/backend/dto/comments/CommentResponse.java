package com.example.backend.dto.comments;

import com.example.backend.domain.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String name,
        String comment,
        LocalDateTime createdAt
) {
    public static CommentResponse create (Comment comment , String name) {
        return new CommentResponse(
                comment.getId(),
                name,
                comment.getComment(),
                comment.getCreatedAt()
        );
    }
}
