package com.example.backend.repository;

import com.example.backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post , Long> {
    List<Post> findByTitleContainingIgnoreCaseOrderByIdDesc(String keyword);

    List<Post> findByContentContainingIgnoreCaseOrderByIdDesc(String keyword);

    List<Post> findByAuthorContainingIgnoreCaseOrderByIdDesc(String keyword);

    Optional<Post> findByIdAndUserId(Long id, Long userId);
}
