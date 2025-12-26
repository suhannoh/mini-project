package com.example.backend.post_comment.repository;

import com.example.backend.post_comment.domain.Comment;
import com.example.backend.post_comment.dto.read.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment , Long> {
    List<Comment> findByPostIdOrderByIdAsc(Long id);

    @Query("""
        select new com.example.backend.post_comment.dto.read.CommentResponse(
            c.id, u.name, c.comment , c.createdAt
        )
        from Comment c
        join User u on u.id = c.userId
        where c.postId = :postId
        order by c.id asc
    """)
    List<CommentResponse> findCommentResponsesByPostId(@Param("postId") Long postId);

    List<Comment> findByUserId(Long userId);
}
