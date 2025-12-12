package com.example.backend.controller;

import com.example.backend.service.UserService;
import com.example.backend.dto.JoinResponse;
import com.example.backend.dto.JoinUserRequest;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://mini-project-0yg2.onrender.com")
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
    public JoinResponse join (@RequestBody JoinUserRequest request) throws IllegalAccessException {
        return userService.joinUser(request);
    }

    // 로그인 api [email,password]
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            return userService.loginUser(request);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);

        }
    }
}
