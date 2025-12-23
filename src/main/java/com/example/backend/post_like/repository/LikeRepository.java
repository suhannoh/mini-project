package com.example.backend.post_like.repository;

import com.example.backend.post_like.domain.Like;
import com.example.backend.post_like.domain.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Like findByUserIdAndPostId(Long userId , Long postId);
    int countByPostIdAndStatus(Long postId, LikeStatus status);
    }
