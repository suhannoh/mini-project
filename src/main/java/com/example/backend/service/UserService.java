package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.error.BusinessException;
import com.example.backend.error.ErrorCode;
import com.example.backend.repository.UserActiveRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.dto.JoinUserRequest;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor // 생성자 주입 X

public class UserService {

    private final UserRepository userRepository;
    private final UserActiveService userActiveService;
    private final UserActiveRepository userActiveRepository;

    public void joinUser(JoinUserRequest request) {

        if(request.getEmail().isBlank() || request.getPassword().isBlank() ||
            request.getName().isBlank()) {
            throw new IllegalArgumentException("USER_JOIN 필수 항목이 비어있습니다 ");
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
        }

        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getName()
        );

        userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail()) //  이메일 없는경우
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_LOGIN_NOT_EMAIL));

        if(!user.getPassword().equals(request.getPassword())) {
            throw new BusinessException(ErrorCode.USER_LOGIN_NOT_PASSWORD);
        }

        userActiveService.saveUserActive(user);
        return new LoginResponse(user);
    }

    @Transactional
    public LoginResponse update (Long id, JoinUserRequest req) {
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