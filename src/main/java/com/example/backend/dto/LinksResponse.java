package com.example.backend.dto;

import com.example.backend.domain.Links;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LinksResponse {
    private long id;
    private long user_id;
    private String notionUrl;
    private String gitHubUrl;
    private String updated_at;
    private String user_name;

    public LinksResponse(Links links, String name) {
        this.id = links.getId();
        this.user_id = links.getUser_id();
        this.notionUrl = links.getNotionUrl();
        this.gitHubUrl = links.getGitHubUrl();
        this.updated_at = links.getUpdated_at();
        this.user_name = name;
    }
}
