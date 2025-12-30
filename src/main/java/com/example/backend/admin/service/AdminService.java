package com.example.backend.admin.service;

import com.example.backend.admin.domain.Notice;
import com.example.backend.admin.domain.UserBlockHistory;
import com.example.backend.admin.dto.notice.NoticeUpdateRequest;
import com.example.backend.admin.dto.user.FindUsersResponse;
import com.example.backend.admin.dto.user.UpdateStatusRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    // 유저 테이블
    private final UserRepository userRepository;
    // 공지 테이블
    private final NoticeRepository noticeRepository;
    // 유저 정지 디테일 테이블
    private final UserBlockHistoryRepository userBlockHistoryRepository;

    // 모든 유저 찾아서 block history와 함께 전달
        // N+1 문제생김 여쭤보기
    @Transactional(readOnly = true)
    public Page<FindUsersResponse> findAllUsers (Pageable pageable) {
        // 페이지 사이즈만큼 가져와 페이지 타입으로 저장
          return userRepository.findAllUsersWithBlockInfo(pageable);
    }

    // 유저 상태변경
    @Transactional
    public void updateUser (UpdateStatusRequest req) {
        // 유저 ID 조회
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND , "user"));
        // 전달받은 role이 있다면 변경
        if(req.role() != null) {
            user.setRole(req.role());
        }
        // 전달받은 status 없다면 종료
        if (req.status() == null) {
            return;
        }
        user.setStatus(req.status());

        // 차단 (BLOCKED) 이라면 유저 정지디테일 테이블에 새롭게 추가 후 저장후 종료
        if(req.status() == Status.BLOCKED) {
            if (req.reason().isBlank()) {
                throw new IllegalArgumentException("정지 사유는 필수입니다");
            }
            UserBlockHistory ubh = UserBlockHistory.create(req);
            userBlockHistoryRepository.save(ubh);
            return;
        }
        // 차단 해제 (ACTIVE)
        if(req.status() == Status.ACTIVE) {
            //변경된 상태가 ACTIVE라면 유저 정지디테일 테이블에서 제일 최근에 블락되어있는 히스토리를 꺼내옴
            UserBlockHistory ubh = userBlockHistoryRepository
                                   .findTopByUserIdAndUnblockedAtIsNullOrderByBlockedAtDesc(req.userId())
                                   .orElse(null);
            // 히스토리가 있다면 정지가 풀린 이력을 현재 시간으로 지정하여 정지가 풀렸음을 저장한다
            if (ubh != null) {
                ubh.setUnblockedAt(LocalDateTime.now());
            }
        }
    }

    // 공지 작성
    @Transactional
    public void createNotice (NoticeRequest req) {
        // 유저 ID 조회
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        // 어드민 권한(Role) 검사
        if (user.getRole() != Role.ADMIN) {
            throw new BusinessException(ErrorCode.FORBIDDEN , "USER는 공지 작성 불가합니다 ");
        }
        // 새로운 공지 엔티티 디비 저장
        Notice notice = Notice.create(req);
        noticeRepository.save(notice);
    }

    // 공지 불러오기
    @Transactional(readOnly = true)
    public Page<NoticeResponse> readNotice (Pageable pageable) {
        return noticeRepository.findAll(pageable).map(NoticeResponse :: create);
    }

    // 활성화된 공지 불러오기
    @Transactional(readOnly = true)
    public List<NoticeResponse> readActiveNotice () {
        return noticeRepository.findByStatus(NoticeStatus.ACTIVE).stream()
                .map(NoticeResponse :: create).toList();
    }

    // 공지 상태 수정
    @Transactional
    public void activeNotice(NoticeUpdateRequest request) {
       Notice notice = noticeRepository.findById(request.id())
               .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));

       notice.setStatus(request.status());
       notice.setNoticeContent(request.noticeContent());
    }

    @Transactional
    public void deleteNotion (Long id) {
        if(id == null) {
            throw new IllegalArgumentException("공지 ID 가 넘어오지 않았습니다");
        }
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTICE_NOT_FOUND));

        noticeRepository.delete(notice);
    }
}
