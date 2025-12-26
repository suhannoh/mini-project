package com.example.backend.admin.service;

import com.example.backend.admin.dto.FindUsersResponse;
import com.example.backend.admin.dto.UpdateStatusRequest;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.user.domain.Role;
import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<FindUsersResponse> findAllUsers () {
        return userRepository.findAll().stream()
                .map(FindUsersResponse::create).toList();
    }

    @Transactional
    public void updateUser (Long id , UpdateStatusRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.setRole(req.role());
        user.setStatus(req.status());
    }
}
