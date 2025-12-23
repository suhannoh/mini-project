package com.example.backend.post.controller;

import com.example.backend.post.domain.Post;
import com.example.backend.post.dto.delete.PostDeleteRequest;
import com.example.backend.post.dto.update.PostUpdateRequest;
import com.example.backend.post.dto.create.PostCreateRequest;
import com.example.backend.post.dto.read.PostResponse;
import com.example.backend.post.dto.search.PostSearchEnum;
import com.example.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/post/health")
    public ResponseEntity<?> healthCheck () {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/post")
    public ResponseEntity<Void> newPost (@RequestBody PostCreateRequest req){
        postService.newPost(req);
        // create / 201
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/post")
    public ResponseEntity<Void> edit (@RequestBody PostUpdateRequest req) {
        postService.edit(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post")
    public List<PostResponse> findAllPost () {
        return postService.findAllPost();
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostDetail(@PathVariable Long id) {
        // 200 / ok
        return ResponseEntity.ok(postService.getPostDetail(id));
    }
    @GetMapping("/post/search")
    public ResponseEntity<List<PostResponse>> search (
            @RequestParam PostSearchEnum type,
            @RequestParam String text
            )
    {
        return ResponseEntity.ok(postService.search(type, text));
    }
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody PostDeleteRequest req) {
        postService.delete(id, req);
        return ResponseEntity.noContent().build();
    }
 }
