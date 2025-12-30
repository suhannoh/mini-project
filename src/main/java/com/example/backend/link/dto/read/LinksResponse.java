package com.example.backend.link.dto.read;

import com.example.backend.link.domain.Link;

import java.time.LocalDateTime;

public record LinksResponse (
     Long id,
     Long user_id,
     String notionUrl,
     String gitHubUrl,
     LocalDateTime updated_at,
     String user_name,
     String gender
) {

}
