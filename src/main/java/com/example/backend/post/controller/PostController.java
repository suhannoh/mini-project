package com.example.backend.post.controller;

import com.example.backend.post.domain.Post;
import com.example.backend.post.dto.delete.PostDeleteRequest;
import com.example.backend.post.dto.update.PostUpdateRequest;
import com.example.backend.post.dto.create.PostCreateRequest;
import com.example.backend.post.dto.read.PostResponse;
import com.example.backend.post.dto.search.PostSearchEnum;
import com.example.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public Page<PostResponse> findAllPost (@RequestParam(defaultValue = "all") String category,
                                           @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        return postService.findAllPost(category,pageable);
    }
    @GetMapping("/post/search")
    public ResponseEntity<Page<PostResponse>> search (
            @RequestParam PostSearchEnum type,
            @RequestParam String text,
            @RequestParam(required = false, defaultValue = "all") String category,
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    )
    {
        return ResponseEntity.ok(postService.search(type, text, category , pageable));
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostDetail(@PathVariable Long id) {
        // 200 / ok
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody PostDeleteRequest req) {
        postService.delete(id, req);
        return ResponseEntity.noContent().build();
    }
 }
