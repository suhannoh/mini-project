package com.example.backend.admin.repository;

import com.example.backend.admin.domain.UserBlockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBlockHistoryRepository extends JpaRepository<UserBlockHistory , Long> {

    // userid , unblock == null  가장 최근에 정지된 기록 1개만
    Optional<UserBlockHistory>
    findTopByUserIdAndUnblockedAtIsNullOrderByBlockedAtDesc(Long userId);
    long countByUserId(Long userId);
}
