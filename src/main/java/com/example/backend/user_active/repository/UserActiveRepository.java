package com.example.backend.user_active.repository;

import com.example.backend.user_active.domain.UserActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserActiveRepository extends JpaRepository<UserActive, Long> {
    List<UserActive> findByLoggedInAtAfterOrderByLoggedInAtDesc(LocalDateTime since);

    void deleteByLoggedInAtBefore(LocalDateTime localDateTime);


    @Modifying
    @Query("update UserActive a set a.userName = :name where a.userId = :userId")
    void updateUserName(@Param("userId") Long userId, @Param("name") String name);
}
