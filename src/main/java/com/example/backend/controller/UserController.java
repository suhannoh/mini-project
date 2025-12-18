package com.example.backend.controller;

import com.example.backend.service.UserService;
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

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Void> join (@RequestBody JoinUserRequest request) {
        userService.joinUser(request);
        // create / 201
        return ResponseEntity.status(201).build();
    }

    // 로그인 api [email,password]
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<LoginResponse> editUser(
            @PathVariable Long id,
            @RequestBody JoinUserRequest req
    ) {
       return ResponseEntity.ok(userService.update(id, req));
    }

    @GetMapping("/health")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
