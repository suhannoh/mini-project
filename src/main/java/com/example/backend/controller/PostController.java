package com.example.backend.controller;

import com.example.backend.domain.Post;
import com.example.backend.dto.posts.PostEditRequestDto;
import com.example.backend.dto.posts.PostRequestDto;
import com.example.backend.dto.posts.PostResponseDto;
import com.example.backend.enums.PostSearchEnum;
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
        // create / 201
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/posts")
    public ResponseEntity<Void> edit (@RequestBody PostEditRequestDto req) {
        postService.edit(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts")
    public List<PostResponseDto> findAllPost () {
        return postService.findAllPost();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostDetail(@PathVariable Long id) {
        // 200 / ok
        return ResponseEntity.ok(postService.getPostDetail(id));
    }
    @GetMapping("/posts/search")
    public ResponseEntity<List<PostResponseDto>> search (
            @RequestParam PostSearchEnum type,
            @RequestParam String text
            )
    {
        return ResponseEntity.ok(postService.search(type, text));
    }
 }
