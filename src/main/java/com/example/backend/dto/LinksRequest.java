package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinksRequest {
    private Long userId;
    private String notionUrl;
    private String gitHubUrl;
}
