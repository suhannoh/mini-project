package com.example.backend.user.controller;

import com.example.backend.user.service.UserService;
import com.example.backend.user.dto.create.UserCreateRequest;
import com.example.backend.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/{id}/edit")
    public ResponseEntity<LoginResponse> editUser(
            @PathVariable Long id,
            @RequestBody UserCreateRequest req
    ) {
       return ResponseEntity.ok(userService.update(id, req));
    }
}
