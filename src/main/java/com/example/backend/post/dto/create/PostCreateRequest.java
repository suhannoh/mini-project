package com.example.backend.post.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
    @NotNull(message = "USER ID는 필수입니다. 프론트에서 확인해주세요")
    private Long userId;
    @NotBlank(message = "제목은 필수입니다")
    private String title;
    @NotBlank(message = "내용은 필수입니다")
    private String content;
    @NotBlank(message = "카테고리는 필수입니다")
    private String category;
    @NotBlank(message = "작성자는 필수입니다. 프론트에서 확인해주세요")
    private String author;
}
