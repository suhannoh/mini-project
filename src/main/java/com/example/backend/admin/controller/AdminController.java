package com.example.backend.admin.controller;

import com.example.backend.admin.dto.FindUsersResponse;
import com.example.backend.admin.dto.UpdateStatusRequest;
import com.example.backend.admin.service.AdminService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<FindUsersResponse>> findAllUser () {

        return ResponseEntity.ok(adminService.findAllUsers());
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<Void> updateUser (@PathVariable Long id ,
                                            @RequestBody UpdateStatusRequest req
                                            ) {
        adminService.updateUser(id, req);
        return ResponseEntity.noContent().build();
    }
}
