package com.example.backend.controller;

import com.example.backend.domain.Post;
import com.example.backend.dto.PostRequestDto;
import com.example.backend.dto.PostResponseDto;
import com.example.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/posts/health")
    public ResponseEntity<?> healthCheck () {
        return ResponseEntity.ok().build();
    }
    @PostMapping("/posts")
    public ResponseEntity<Void> newPost (@RequestBody PostRequestDto req){
        postService.newPost(req);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/posts")
    public List<PostResponseDto> findAllPost () {
        return postService.findAllPost();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostDetail(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostDetail(id));
    }
 }
