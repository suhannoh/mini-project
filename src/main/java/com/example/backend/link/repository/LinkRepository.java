package com.example.backend.link.repository;

import com.example.backend.link.domain.Link;
import com.example.backend.link.dto.read.LinksResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link,Long> {
    Optional<Link> findByUserId(Long userId);

    @Query("""
    SELECT new com.example.backend.link.dto.read.LinksResponse(
        l.id, l.userId, l.notionUrl, l.gitHubUrl , l.updatedAt,
        u.name , u.gender
    )
    FROM Link l
    JOIN User u ON u.id = l.userId
""")
    List<LinksResponse> findAllLinkWithUserInfo();
}
