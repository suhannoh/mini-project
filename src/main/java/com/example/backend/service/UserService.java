package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.repository.UserActiveRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.dto.JoinResponse;
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

    public JoinResponse joinUser(JoinUserRequest request) {
        if(request.getEmail().isBlank() || request.getPassword().isBlank() ||
            request.getName().isBlank()) {
            throw new IllegalArgumentException("공백 오류 ,,");
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getName()
        );

        userRepository.save(user);
        return new JoinResponse(user);
    }

    public LoginResponse loginUser(LoginRequest request) throws IllegalAccessException {
        User user = userRepository.findUserByEmail(request.getEmail()) //  이메일 없는경우
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if(!user.getPassword().equals(request.getPassword())) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다");
        }
        userActiveService.saveUserActive(user);
        return new LoginResponse(user);
    }

    @Transactional
    public LoginResponse update (Long id, JoinUserRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getName() != null) user.setName(req.getName());
        if (req.getPhone() != null) user.setPhone(req.getPhone());

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            if (req.getPassword().length() < 5) {
                throw new IllegalArgumentException("비밀번호는 5자 이상");
            }
            user.setPassword(req.getPassword());
        }
        userActiveRepository.updateUserName(id, req.getName());
        return new LoginResponse(user);
    }
}