package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.domain.UserActive;
import com.example.backend.repository.UserActiveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActiveService {

    private final UserActiveRepository userActiveRepository;

    public void saveUserActive (User res) {
        userActiveRepository.save(
                new UserActive(res.getId(), res.getName(), LocalDateTime.now())
        );
    }

    @Transactional
    public List<UserActive> userActive() {
        userActiveRepository.deleteByLoggedInAtBefore(LocalDateTime.now().minusHours(2));

        return userActiveRepository.findByLoggedInAtAfterOrderByLoggedInAtDesc(
                LocalDateTime.now().minusHours(1)
        );
    }
}
