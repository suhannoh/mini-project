package com.example.backend.domain;

import com.example.backend.dto.posts.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="title" ,  nullable = false)
    private String title;
    @Column(name="content", columnDefinition = "TEXT",  nullable = false)
    private String content;
    @Column(name="category" ,  nullable = false)
    private String category;
    @Column(name="author" ,  nullable = false)
    private String author;
    @Column(name="created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Post create (PostRequestDto req) {
        Post post = new Post();
        post.setUserId(req.getUserId());
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setCategory(req.getCategory());
        post.setAuthor(req.getAuthor());
        return post;
    }
}
