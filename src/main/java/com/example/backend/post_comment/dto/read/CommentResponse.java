package com.example.backend.post_comment.dto.read;

import com.example.backend.post_comment.domain.Comment;

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
