package com.example.backend.repository;

import com.example.backend.domain.UserActive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserActiveRepository extends JpaRepository<UserActive , Long> {
    List<UserActive> findByLoggedInAtAfterOrderByLoggedInAtDesc(LocalDateTime since);

    void deleteByLoggedInAtBefore(LocalDateTime localDateTime);

}
