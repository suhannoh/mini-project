package com.example.backend.admin.controller;

import com.example.backend.admin.dto.notice.NoticeUpdateRequest;
import com.example.backend.admin.dto.user.FindUsersResponse;
import com.example.backend.admin.dto.user.UpdateStatusRequest;
import com.example.backend.admin.dto.notice.NoticeRequest;
import com.example.backend.admin.dto.notice.NoticeResponse;
import com.example.backend.admin.dto.notice.NoticeStatus;
import com.example.backend.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    ///  회원 //////////////////////

    // 회원 관리페이지 [전체] 회원 불러오기
    @GetMapping("/users")
    public ResponseEntity<Page<FindUsersResponse>> findAllUser (
            @PageableDefault(size = 10, sort = "id" , direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(adminService.findAllUsers(pageable));
    }

    // 회원 관리페이지 회원 상태 수정 [권한 , 계정상태]
    @PatchMapping("/user")
    public ResponseEntity<Void> updateUser (@Valid @RequestBody UpdateStatusRequest request) {
        adminService.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    ///  공지 //////////////////////

    // 공지 작성
    @PostMapping("/notice")
    public ResponseEntity<Void> createNotice (@Valid @RequestBody NoticeRequest request) {
        adminService.createNotice(request);
        return ResponseEntity.ok().build();
    }

    // 모든 공지 페이지사이즈만큼 불러오기
    @GetMapping("/notice")
    public ResponseEntity<Page<NoticeResponse>> readNotice (
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(adminService.readNotice(pageable));
    }

    // 활성화(Active) 된 공지 불러오기
    @GetMapping("/notice/active")
    public ResponseEntity<List<NoticeResponse>> readActiveNotice () {
        return ResponseEntity.ok(adminService.readActiveNotice());
    }

    //공지 상태 수정
    @PatchMapping("/notice")
    public  ResponseEntity<Void> activeNotice (@Valid @RequestBody NoticeUpdateRequest request) {
        adminService.activeNotice(request);
        return ResponseEntity.ok().build();
    }

    //공지 삭제
    @DeleteMapping("/notice")
    public ResponseEntity<Void> deleteNotice(@RequestParam Long id) {
        adminService.deleteNotion(id);
        return ResponseEntity.noContent().build();
    }
}
