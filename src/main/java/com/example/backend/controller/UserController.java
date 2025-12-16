package com.example.backend.controller;

import com.example.backend.service.UserService;
import com.example.backend.dto.JoinResponse;
import com.example.backend.dto.JoinUserRequest;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // Test api
    @GetMapping("/test")
    public String test () {
        return "api 연결 성공";
    }

    // 회원가입 api [email,password,name,phone]
    @PostMapping("/join")
    public JoinResponse join (@RequestBody JoinUserRequest request) {
        return userService.joinUser(request);
    }

    // 로그인 api [email,password]
    // 너도 내 서버 주소로 날리면 이 정보 얻어갈 수 있어 이게 서버야
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            return userService.loginUser(request);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);

        }
    }
    @PostMapping("/{id}/edit")
    public LoginResponse editUser(
            @PathVariable Long id,
            @RequestBody JoinUserRequest req
    ) {
       return userService.update(id, req);
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
