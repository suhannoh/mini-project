package com.example.backend.post_like.controller;

import com.example.backend.post_like.domain.LikeStatus;
import com.example.backend.post_like.dto.LikeCreateRequest;
import com.example.backend.post_like.dto.LikeResponse;
import com.example.backend.post_like.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/like")
    public ResponseEntity<LikeResponse> createLike (@Valid @RequestBody LikeCreateRequest req) {
        return ResponseEntity.ok(likeService.createLike(req));
    }

    @GetMapping("/post/like/count")
    public ResponseEntity<Integer> getCountLike (@RequestParam Long postId) {
        return ResponseEntity.ok(likeService.getCountLike(postId));
    }

    @PostMapping("/post/like/read")
    public ResponseEntity<LikeResponse> readLike (@Valid @RequestBody LikeCreateRequest req) {
        return ResponseEntity.ok(likeService.readLike(req));
    }
}
