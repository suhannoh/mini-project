package com.example.backend.controller;

import com.example.backend.domain.User;
import com.example.backend.error.BusinessException;
import com.example.backend.error.ErrorCode;
import com.example.backend.service.UserService;
import com.example.backend.dto.auth.JoinUserRequest;
import com.example.backend.dto.auth.LoginRequest;
import com.example.backend.dto.auth.LoginResponse;
import jakarta.servlet.http.HttpSession;
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

    // 로그인 api [email,password] + SESSION 변경
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {
        User user = userService.loginUser(request);

        session.setAttribute("LOGIN_USER_ID" , user.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me (HttpSession session) {
        Long userId = (Long) session.getAttribute("LOGIN_USER_ID");
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_SESSION);
        }
        User user = userService.findById(userId);
        return ResponseEntity.ok(new LoginResponse(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
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
