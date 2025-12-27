package com.example.backend.admin.dto.notice;

import com.example.backend.admin.domain.Notice;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long id,
        String noticeContent,
        Long userId,
        NoticeStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static NoticeResponse create (Notice notice) {
        return new NoticeResponse (
                notice.getId(),
                notice.getNoticeContent(),
                notice.getAdminId(),
                notice.getStatus(),
                notice.getCreatedAt(),
                notice.getUpdatedAt()
        );
    };
}
