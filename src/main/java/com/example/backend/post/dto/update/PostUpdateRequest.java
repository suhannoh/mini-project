package com.example.backend.post.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUpdateRequest(
        @NotNull(message = "POST ID는 필수입니다. 프론트에서 확인해주세요")
        Long postId,
        @NotNull(message = "USER ID는 필수입니다. 프론트에서 확인해주세요")
        Long userId,
        @NotBlank(message = "제목은 필수입니다")
        String title,
        @NotBlank(message = "내용은 필수입니다")
        String content,
        @NotBlank(message = "카테고리는 필수입니다")
        String category,
        @NotBlank(message = "작성자는 필수입니다. 프론트에서 확인해주세요")
        String author
) {
}
