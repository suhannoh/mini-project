package com.example.backend.auth.service;


import com.example.backend.auth.dto.LoginResponse;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.user.domain.Status;
import com.example.backend.user.domain.User;
import com.example.backend.user.dto.create.UserCreateRequest;
import com.example.backend.auth.dto.LoginRequest;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user_active.service.UserActiveService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // 생성자 주입 X

public class AuthService {

    // 유저 테이블
    private final UserRepository userRepository;
    // 접속중인 유저 테이블
    private final UserActiveService userActiveService;

    // 회원가입
    @Transactional
    public void signUp(UserCreateRequest request) {
        // 값 검사
        if(request.getEmail().isBlank() || request.getPassword().isBlank() ||
                request.getName().isBlank()) {
            throw new IllegalArgumentException("USER_JOIN 필수 항목이 비어있습니다 ");
        }
        // 중복검사
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
        }
        // 새로운 유저 객체 생성
        User user = new User();
        // 객체에 정보 저장
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        // 유저 테이블에 저장
        userRepository.save(user);
    }

    // 로그인
    @Transactional
    public LoginResponse login(LoginRequest request) {
        //  이메일로 찾았는데 없는경우 에러 던지기
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_LOGIN_NOT_EMAIL));
        //  이메일로 찾은 객체와 비밀번호가 다른경우 에러 던지기
        if(!user.getPassword().equals(request.getPassword())) {
            throw new BusinessException(ErrorCode.USER_LOGIN_NOT_PASSWORD);
        }
        //  유저 객체의 계정 상태가 블락인경우 에러 던지기
        if(user.getStatus() == Status.BLOCKED) {
            throw new BusinessException(ErrorCode.BLOCKED_STATUS);
        }
        // 마지막 접속일 저장
        user.setLastLoginAt(LocalDateTime.now());
        // 현재 접속 테이블에 저장하기위해 객체 전달
        userActiveService.saveUserActive(user);
        // 위 조건 통과 시 찾은 유저객체 반환
        return new LoginResponse(user);
    }

    @Transactional(readOnly = true)
    public LoginResponse findBySessionId (Long userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_SESSION);
        }
         User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

         return new LoginResponse(user);
    }
}