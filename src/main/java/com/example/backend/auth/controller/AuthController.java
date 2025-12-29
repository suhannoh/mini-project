package com.example.backend.auth.controller;

import com.example.backend.auth.service.AuthService;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.user.domain.User;
import com.example.backend.user.dto.create.UserCreateRequest;
import com.example.backend.auth.dto.LoginRequest;
import com.example.backend.auth.dto.LoginResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Void> join (@RequestBody UserCreateRequest request) {
        // 회원가입 로직
        authService.signUp(request);
        // 로직 실행 후 (create / 201)
        return ResponseEntity.status(201).build();
    }

    // 로그인 api [email,password] + SESSION 변경
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        // 로그인 로직 후 유저 객체 반환
        LoginResponse user = authService.login(request);
        // 전달받은 세션에 LOGIN_USER_ID 이름에 user의 고유 id 저장
        session.setAttribute("LOGIN_USER_ID" , user.getId());
        // 로직 실행 후 (ok / 200 / LoginResponse dto)
        return ResponseEntity.ok(user);
    }

    //세션 인증 [로그인 ,url 직접 이동 시]
    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me (HttpSession session) {
        // LOGIN_USER_ID 이름에 user의 고유 id 꺼내서 서비스로 전달
        Long userId = (Long) session.getAttribute("LOGIN_USER_ID");
        // 로직 실행 후 (ok / 200 / LoginResponse dto)
        return ResponseEntity.ok(authService.findBySessionId(userId));
    }

    // 세션 종료 [로그아웃]
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
    // 메인페이지 헬스체크
    @GetMapping("/health")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }

    // 로그인페이지 핑 체크
    @GetMapping("/api/ping")
    public ResponseEntity<Void> ping () {
        return ResponseEntity.ok().build();
    }
}
