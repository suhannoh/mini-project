package com.example.backend.link.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinksRequest {
    @NotNull(message = "USER ID가 비어있습니다 , 재로그인 후 다시 시도해주세요")
    private Long userId;
    private String notionUrl;
    private String gitHubUrl;
}
