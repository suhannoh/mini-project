package com.example.backend.admin.repository;

import com.example.backend.admin.domain.Notice;
import com.example.backend.admin.dto.notice.NoticeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {

    List<Notice> findByStatus(NoticeStatus status);
}
