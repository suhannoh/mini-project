package com.example.backend.admin.dto.notice;

public record NoticeRequest(
        Long userId,
        String noticeContent
) {
}
