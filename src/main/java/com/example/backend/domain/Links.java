package com.example.backend.domain;

import com.example.backend.dto.LinksRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "links")
@Getter
@NoArgsConstructor
public class Links {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "notion_url")
    private String notionUrl;
    @Column (name = "git_hub_url")
    private String gitHubUrl;
    @Column (name = "user_id")
    private long user_id;
    private String created_at;
    private String updated_at;

    public Links (String notionUrl , String gitHubUrl , long user_id) {
        this.notionUrl = notionUrl;
        this.gitHubUrl = gitHubUrl;
        this.user_id = user_id;
    }
}
