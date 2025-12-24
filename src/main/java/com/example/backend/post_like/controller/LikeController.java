package com.example.backend.post_like.controller;

import com.example.backend.post_like.dto.LikeCreateRequest;
import com.example.backend.post_like.dto.LikeResponse;
import com.example.backend.post_like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/like")
    public ResponseEntity<LikeResponse> create (@RequestBody LikeCreateRequest req) {
        return ResponseEntity.ok(likeService.like(req));
    }

    @PostMapping("/post/like/read")
    public ResponseEntity<LikeResponse> read (@RequestBody LikeCreateRequest req) {
        return ResponseEntity.ok(likeService.read(req));
    }
}
