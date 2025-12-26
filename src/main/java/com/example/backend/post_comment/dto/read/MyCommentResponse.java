package com.example.backend.post_comment.dto.read;

import com.example.backend.post_comment.domain.Comment;

import java.time.LocalDateTime;

public record MyCommentResponse(
        Long id,
        Long postId,
        Long userId,
        String comment,
        LocalDateTime createdAt
) {
    public static MyCommentResponse create (Comment comment) {
        return new MyCommentResponse(
                comment.getId(),
                comment.getPostId(),
                comment.getUserId(),
                comment.getComment(),
                comment.getCreatedAt()
        );
    }
}
