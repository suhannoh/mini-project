package com.example.backend.user.controller;

import com.example.backend.user.dto.find.UserFIndResponse;
import com.example.backend.user.dto.find.UserFindRequest;
import com.example.backend.user.service.UserService;
import com.example.backend.auth.dto.signup.SignUpRequest;
import com.example.backend.auth.dto.login.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/{id}/edit")
    public ResponseEntity<LoginResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody SignUpRequest req
    ) {
       return ResponseEntity.ok(userService.updateUser(id, req));
    }

    @PostMapping("/find/password")
    public ResponseEntity<UserFIndResponse> findPassword (@Valid @RequestBody UserFindRequest req) {
        return ResponseEntity.ok(userService.findPassword(req));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser (@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
