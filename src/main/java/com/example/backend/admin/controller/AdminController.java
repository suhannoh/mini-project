package com.example.backend.admin.controller;

import com.example.backend.admin.dto.FindUsersResponse;
import com.example.backend.admin.dto.UpdateStatusRequest;
import com.example.backend.admin.dto.notice.NoticeRequest;
import com.example.backend.admin.dto.notice.NoticeResponse;
import com.example.backend.admin.dto.notice.NoticeStatus;
import com.example.backend.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<FindUsersResponse>> findAllUser () {

        return ResponseEntity.ok(adminService.findAllUsers());
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<Void> updateUser (@PathVariable Long id ,
                                            @RequestBody UpdateStatusRequest req
                                            ) {
        adminService.updateUser(id, req);
        return ResponseEntity.noContent().build();
    }
    //공지 작성
    @PostMapping("/notice")
    public ResponseEntity<Void> createNotice (@RequestBody NoticeRequest req) {
        adminService.createNotice(req);
        return ResponseEntity.ok().build();
    }
    //공지 불러오기
    @GetMapping("/notice")
    public ResponseEntity<List<NoticeResponse>> readNotice () {
        return ResponseEntity.ok(adminService.readNotice());
    }
    //Active 공지 불러오기
    @GetMapping("/notice/active")
    public ResponseEntity<List<NoticeResponse>> readActiveNotice () {
        return ResponseEntity.ok(adminService.readActiveNotice());
    }
    //공지 상태 수정
    @PatchMapping("/notice")
    public  ResponseEntity<Void> activeNotice (@RequestParam Long id,
                                               @RequestParam NoticeStatus status,
                                               @RequestParam String noticeContent) {
        adminService.activeNotice(id , status , noticeContent);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/notice")
    public ResponseEntity<Void> deleteNotice(@RequestParam Long id) {
        adminService.deleteNotion(id);
        return ResponseEntity.noContent().build();
    }
}
