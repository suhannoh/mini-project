package com.example.backend.post.dto.create;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
    private Long userId;
    private String title;
    private String content;
    private String category;
    private String author;
}
