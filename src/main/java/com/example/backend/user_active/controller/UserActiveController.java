package com.example.backend.user_active.controller;

import com.example.backend.user_active.domain.UserActive;
import com.example.backend.user_active.service.UserActiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserActiveController {

    private final UserActiveService userActiveService;


    @GetMapping("/useractive")
    public List<UserActive> userActive() {
        return userActiveService.userActive();
    }
    @GetMapping("/useractive/health")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
