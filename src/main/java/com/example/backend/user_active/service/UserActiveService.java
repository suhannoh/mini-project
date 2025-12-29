package com.example.backend.user_active.service;

import com.example.backend.user.domain.User;
import com.example.backend.user_active.domain.UserActive;
import com.example.backend.user_active.repository.UserActiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActiveService {

    private final UserActiveRepository userActiveRepository;

    @Transactional
    public void saveUserActive (User res) {
        userActiveRepository.save(
                new UserActive(res.getId(), res.getName(), LocalDateTime.now())
        );
    }

    @Transactional
    public List<UserActive> getActiveUser() {
        userActiveRepository.deleteByLoggedInAtBefore(LocalDateTime.now().minusHours(2));

        return userActiveRepository.findByLoggedInAtAfterOrderByLoggedInAtDesc(
                LocalDateTime.now().minusHours(1)
        );
    }
}
