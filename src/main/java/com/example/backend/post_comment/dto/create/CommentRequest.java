package com.example.backend.post_comment.dto.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotNull(message = "POST ID가 비어있습니다. 프론트에서 확인해주세요 ")
        @NotNull
        Long postId,
        @NotNull(message = "USER ID가 비어있습니다. 로그인을 다시 해주세요 ")
        Long userId,
        @NotBlank(message = "공백은 입력할 수 없습니다")
        String comment
) {
}
