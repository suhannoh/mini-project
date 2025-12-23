package com.example.backend.post.dto.read;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private String category;
    private String author;
    private LocalDateTime createdAt;

    public PostResponse(
            Long postId, String title, String content ,
            String category, String author, LocalDateTime createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.createdAt = createdAt;
    }
}
