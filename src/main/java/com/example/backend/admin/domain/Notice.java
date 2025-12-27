package com.example.backend.admin.domain;

import com.example.backend.admin.dto.notice.NoticeRequest;
import com.example.backend.admin.dto.notice.NoticeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "admin_id")
    private Long adminId;
    @Column(name = "notice_content")
    private String noticeContent;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NoticeStatus status = NoticeStatus.INACTIVE;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Notice create (NoticeRequest req){
        Notice notice = new Notice();
        notice.setAdminId(req.userId());
        notice.setNoticeContent(req.noticeContent());
        return notice;
    }
}
