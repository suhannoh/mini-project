package com.example.backend.user.service;

import com.example.backend.user.domain.User;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.user.dto.find.UserFIndResponse;
import com.example.backend.user.dto.find.UserFindRequest;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.auth.dto.signup.SignUpRequest;
import com.example.backend.auth.dto.login.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor // 생성자 주입 X

public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public LoginResponse updateUser (Long id, SignUpRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!req.getEmail().equals(user.getEmail())
                    && userRepository.existsByEmail(req.getEmail())) {
                throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
            }

        user.setEmail(req.getEmail());

        if (!user.getName().equals(req.getName())) user.setName(req.getName());
        if (req.getPhone() != null && !user.getPhone().equals(req.getPhone())) user.setPhone(req.getPhone());
        if (!user.getGender().equals(req.getGender())) user.setGender(req.getGender());

        user.setPassword(req.getPassword());
        return new LoginResponse(user);
    }

    @Transactional(readOnly = true)
    public UserFIndResponse findPassword(UserFindRequest req) {

        User user = userRepository.findByEmailAndName(req.email(), req.name())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserFIndResponse("",user.getPassword());
    }

    @Transactional
    public void deleteUser (Long id) {
        if(id == null) throw new IllegalArgumentException("UESR ID가 비어있습니다");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }
}