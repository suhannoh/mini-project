package com.example.backend.admin.service;

import com.example.backend.admin.domain.Notice;
import com.example.backend.admin.domain.UserBlockHistory;
import com.example.backend.admin.dto.FindUsersResponse;
import com.example.backend.admin.dto.UpdateStatusRequest;
import com.example.backend.admin.dto.notice.NoticeRequest;
import com.example.backend.admin.dto.notice.NoticeResponse;
import com.example.backend.admin.dto.notice.NoticeStatus;
import com.example.backend.admin.repository.NoticeRepository;
import com.example.backend.admin.repository.UserBlockHistoryRepository;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.user.domain.Role;
import com.example.backend.user.domain.Status;
import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserBlockHistoryRepository userBlockHistoryRepository;

    // 모든 유저 찾아서 block history와 함께 전달
    public List<FindUsersResponse> findAllUsers () {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserBlockHistory ubh =
                            userBlockHistoryRepository.findTopByUserIdAndUnblockedAtIsNullOrderByBlockedAtDesc(user.getId())
                                    .orElse(null);
                    long count = userBlockHistoryRepository.countByUserId(user.getId());
                    return FindUsersResponse.create(user, ubh , count);
                }).toList();
    }
    // 유저 상태변경
    @Transactional
    public void updateUser (UpdateStatusRequest req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND , "user"));
        user.setRole(req.role());
        if(req.status() == null) {
            return;
        }
        user.setStatus(req.status());
        if(req.status() == Status.BLOCKED) {
            UserBlockHistory ubh = UserBlockHistory.create(req);
            userBlockHistoryRepository.save(ubh);
            return;
        }
            UserBlockHistory ubh = userBlockHistoryRepository.findTopByUserIdAndUnblockedAtIsNullOrderByBlockedAtDesc(req.userId())
                    .orElse(null);
            if (ubh != null) {
                ubh.setUnblockedAt(LocalDateTime.now());
            }
    }

    // 공지 작성
    public void createNotice (NoticeRequest req) {
        // 전달받은 값 유효성검사
        if(req.userId() == null || req.noticeContent().isBlank()) {
            throw new IllegalArgumentException("전달 값이 유효하지 않습니다 ");
        }
        // 유저 있는지 검사
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        // 권한 검사
        if (user.getRole() != Role.ADMIN) {
            throw new BusinessException(ErrorCode.FORBIDDEN , "USER는 공지 작성 불가합니다 ");
        }

        Notice notice = Notice.create(req);
        noticeRepository.save(notice);
    }
    // 공지 불러오기
    public List<NoticeResponse> readNotice () {
        return noticeRepository.findAll().stream()
                .map(NoticeResponse :: create).toList();
    }
    // 활성화된 공지 불러오기
    public List<NoticeResponse> readActiveNotice () {
        return noticeRepository.findByStatus(NoticeStatus.ACTIVE).stream()
                .map(NoticeResponse :: create).toList();
    }
    // 공지 상태 수정
    @Transactional
    public void activeNotice(Long id , NoticeStatus status , String noticeContent) {
       Notice notice = noticeRepository.findById(id)
               .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));
       if(status == null || noticeContent.isBlank()) {
           throw new IllegalArgumentException("공백으로 변경 할 수 없습니다");
       }
       notice.setStatus(status);
       notice.setNoticeContent(noticeContent);
    }

    public void deleteNotion (Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));

        noticeRepository.delete(notice);
    }
}
