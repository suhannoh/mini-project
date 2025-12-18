package com.example.backend.domain;

import com.example.backend.dto.comments.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="user_id" , nullable = false)
    private Long userId;
    @Column(name="post_id", nullable = false)
    private Long postId;
    @Column(name="comment", nullable = false)
    private String comment;
    @Column(name="created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static Comment create (CommentRequestDto req) {
        Comment comment = new Comment();
        comment.postId = req.postId();
        comment.userId = req.userId();
        comment.comment = req.comment();
        return comment;
    }
}
