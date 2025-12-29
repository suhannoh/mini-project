package com.example.backend.post.dto.read;

import com.example.backend.post.dto.search.PostSearchEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record PostSearchRequest(
        @NotNull(message = "검색 타입을 설정해주세요")
        PostSearchEnum type,
        @NotBlank(message = "검색 할 내용을 입력해주세요")
        String text,
        String category
        ) {
}
