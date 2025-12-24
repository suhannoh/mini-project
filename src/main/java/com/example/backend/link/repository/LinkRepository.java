package com.example.backend.link.repository;

import com.example.backend.link.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link,Long> {
    Optional<Link> findByUserId(Long userId);
}
