package com.example.backend.dto.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String category;
    private String author;

    public PostResponseDto (
            Long postId, String title, String content ,
            String category, String author) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
    }
}
