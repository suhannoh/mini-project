package com.example.backend.repository;

import com.example.backend.domain.Links;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinksRepository extends JpaRepository<Links,Long> {
    Optional<Links> findByUserId(Long userId);
}
