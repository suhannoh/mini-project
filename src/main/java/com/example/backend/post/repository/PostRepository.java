package com.example.backend.post.repository;

import com.example.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post , Long> {
    List<Post> findByTitleContainingIgnoreCaseOrderByIdDesc(String keyword);

    List<Post> findByContentContainingIgnoreCaseOrderByIdDesc(String keyword);

    List<Post> findByAuthorContainingIgnoreCaseOrderByIdDesc(String keyword);

    Optional<Post> findByIdAndUserId(Long id, Long userId);
}
