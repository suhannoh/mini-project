package com.example.backend.domain;

import com.example.backend.dto.LinksRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table (name = "links")
@Getter
@Setter
@NoArgsConstructor
public class Links {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "notion_url")
    private String notionUrl;
    @Column (name = "git_hub_url")
    private String gitHubUrl;
    @Column (name = "user_id", unique = true)
    private long userId;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Links (String notionUrl , String gitHubUrl , long user_id) {
        this.notionUrl = notionUrl;
        this.gitHubUrl = gitHubUrl;
        this.userId = user_id;
    }
}
