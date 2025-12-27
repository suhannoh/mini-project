package com.example.backend.auth.service;


import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.user.domain.Status;
import com.example.backend.user.domain.User;
import com.example.backend.user.dto.create.UserCreateRequest;
import com.example.backend.auth.dto.LoginRequest;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user_active.service.UserActiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // 생성자 주입 X

public class AuthService {

    private final UserRepository userRepository;
    private final UserActiveService userActiveService;

    public void joinUser(UserCreateRequest request) {
        // 값 검사
        if(request.getEmail().isBlank() || request.getPassword().isBlank() ||
                request.getName().isBlank()) {
            throw new IllegalArgumentException("USER_JOIN 필수 항목이 비어있습니다 ");
        }
        // 중복검사
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());

        userRepository.save(user);
    }

    @Transactional
    public User loginUser(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail()) //  이메일 없는경우
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_LOGIN_NOT_EMAIL));

        if(!user.getPassword().equals(request.getPassword())) {
            throw new BusinessException(ErrorCode.USER_LOGIN_NOT_PASSWORD);
        }

        if(user.getStatus() == Status.BLOCKED) {
            throw new BusinessException(ErrorCode.BLOCKED_STATUS);
        }

        user.setLastLoginAt(LocalDateTime.now());
        userActiveService.saveUserActive(user);
        return user;
    }

    public User findById (Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}