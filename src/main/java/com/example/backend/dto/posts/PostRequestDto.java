package com.example.backend.dto.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private Long userId;
    private String title;
    private String content;
    private String category;
    private String author;
}
