package com.example.backend.link.dto.read;

import com.example.backend.link.domain.Link;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class LinksResponse {
    private Long id;
    private Long user_id;
    private String notionUrl;
    private String gitHubUrl;
    private LocalDateTime updated_at;
    private String user_name;

    public LinksResponse(Link link, String name) {
        this.id = link.getId();
        this.user_id = link.getUserId();
        this.notionUrl = link.getNotionUrl();
        this.gitHubUrl = link.getGitHubUrl();
        this.updated_at = link.getUpdatedAt();
        this.user_name = name;
    }
}
