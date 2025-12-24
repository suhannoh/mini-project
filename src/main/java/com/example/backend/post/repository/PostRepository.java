package com.example.backend.post.repository;

import com.example.backend.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post , Long> {
//    Page<Post> findByTitleContainingIgnoreCaseOrderByIdDesc(String keyword , Pageable pageable);
//
//    Page<Post> findByContentContainingIgnoreCaseOrderByIdDesc(String keyword , Pageable pageable);
//
//    Page<Post> findByAuthorContainingIgnoreCaseOrderByIdDesc(String keyword, Pageable pageable);

//    Page<Post> findAllByOrderByIdDesc(org.springframework.data.domain.Pageable pageable);
    Page<Post> findByCategoryOrderByIdDesc(String category, Pageable pageable);

    Page<Post> findByTitleContainingIgnoreCase(String text, Pageable pageable);

    Page<Post> findByCategoryAndTitleContainingIgnoreCase(String cat, String text, Pageable pageable);

    Page<Post> findByContentContainingIgnoreCase(String text, Pageable pageable);

    Page<Post> findByCategoryAndContentContainingIgnoreCase(String cat, String text, Pageable pageable);

    Page<Post> findByAuthorContainingIgnoreCase(String text, Pageable pageable);

    Page<Post> findByCategoryAndAuthorContainingIgnoreCase(String cat, String text, Pageable pageable);
//    List<Post> findByCategoryOrderByIdDesc(String category);
}
