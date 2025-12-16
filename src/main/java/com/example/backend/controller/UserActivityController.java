package com.example.backend.controller;

import com.example.backend.domain.UserActive;
import com.example.backend.dto.LoginResponse;
import com.example.backend.service.UserActiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActiveService userActiveService;


    @GetMapping("/useractive")
    public List<UserActive> userActive() {
        return userActiveService.userActive();
    }
    @GetMapping("/useractive/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
