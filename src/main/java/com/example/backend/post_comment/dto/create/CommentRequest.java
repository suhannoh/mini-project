package com.example.backend.post_comment.dto.create;


public record CommentRequest(
        Long postId,
        Long userId,
        String comment
) {
}
