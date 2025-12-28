package com.example.backend.user.service;

import com.example.backend.user.domain.User;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.user.dto.find.UserFIndResponse;
import com.example.backend.user.dto.find.UserFindRequest;
import com.example.backend.user_active.repository.UserActiveRepository;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user.dto.create.UserCreateRequest;
import com.example.backend.auth.dto.LoginResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor // 생성자 주입 X

public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public LoginResponse update (Long id, UserCreateRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (req.getEmail() != null && !req.getEmail().isBlank()) {
            if (!req.getEmail().equals(user.getEmail())
                    && userRepository.existsByEmail(req.getEmail())) {
                throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
            }
            user.setEmail(req.getEmail());
        }
        if (req.getName() != null && !req.getName().isBlank()) user.setName(req.getName());
        if (req.getPhone() != null && !req.getPassword().isBlank()) user.setPhone(req.getPhone());
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            if (req.getPassword().length() < 5) {
                throw new IllegalArgumentException("비밀번호가 5자리보다 짧습니다");
            }
            user.setPassword(req.getPassword());
        }
        user.setGender(req.getGender());
        user.setUpdatedAt(LocalDateTime.now());
//        userActiveRepository.updateUserName(id, req.getName());
        return new LoginResponse(user);
    }

    public UserFIndResponse findPassword(UserFindRequest req) {
        if(req.email().isBlank() || req.name().isBlank()) {
            throw new IllegalArgumentException("빈 칸은 입력할 수 없습니다");
        }

        User user = userRepository.findByEmailAndName(req.email(), req.name())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        
        return new UserFIndResponse("",user.getPassword());
    }

    public void deleteAccount (Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }
}