package com.example.backend.user.service;

import com.example.backend.user.domain.User;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.user_active.repository.UserActiveRepository;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user.dto.create.UserCreateRequest;
import com.example.backend.auth.dto.LoginResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor // 생성자 주입 X

public class UserService {

    private final UserRepository userRepository;
    private final UserActiveRepository userActiveRepository;

    @Transactional
    public LoginResponse update (Long id, UserCreateRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getName() != null) user.setName(req.getName());
        if (req.getPhone() != null) user.setPhone(req.getPhone());

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            if (req.getPassword().length() < 5) {
                throw new IllegalArgumentException("비밀번호가 5자리보다 짧습니다");
            }
            user.setPassword(req.getPassword());
        }
        userActiveRepository.updateUserName(id, req.getName());
        return new LoginResponse(user);
    }
    

}