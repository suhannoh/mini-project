package com.example.backend.admin.dto.notice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NoticeUpdateRequest(
        @NotNull(message = "변경하는 어드민 ID는 필수입니다. 프론트에서 확인해주세요")
        Long id,
        @NotNull(message = "STATUS(상태) 는 필수입니다")
        NoticeStatus status,
        @NotBlank(message = "공지 내용은 필수입니다")
        @Size(min = 10 , message = "공지는 10자 이상으로 작성하여주세요")
        String noticeContent
) {
}
