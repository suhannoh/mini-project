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

    // 메인페이지 [POST API] 헬스 체크
    @GetMapping("/post/health")
    public ResponseEntity<?> healthCheck () {
        return ResponseEntity.ok().build();
    }

    // 글 작성
    @PostMapping("/post")
    public ResponseEntity<Void> newPost (@RequestBody PostCreateRequest req){
        postService.newPost(req);
        // create / 201
        return ResponseEntity.status(201).build();
    }
    // 글 수정
    @PutMapping("/post")
    public ResponseEntity<Void> edit (@RequestBody PostUpdateRequest req) {
        postService.edit(req);
        return ResponseEntity.ok().build();
    }
    // 글 불러오기 + 카테고리 (size 씩 id 순으로 정렬하여 가져오기)
    @GetMapping("/post")
    public Page<PostResponse> findAllPost (@RequestParam(defaultValue = "all") String category,
                                           @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        return postService.findAllPost(category,pageable);
    }
    // 글 찾아오기 . 카테고리 + 검색타입 + 검색어 + 정렬페이징
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

    // 글 찾아오기 . 작성자명 익명인 것만
    @GetMapping("/post/anonymous")
    public ResponseEntity<List<PostResponse>> findAllAnon (@RequestParam(defaultValue = "all") String category) {
        return ResponseEntity.ok(postService.findAllAnon(category));
    }

    // 특정 글 상세 불러오기
    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostDetail(@PathVariable Long id) {
        // 200 / ok
        return ResponseEntity.ok(postService.getPostDetail(id));
    }
    // 특정 글 삭제 [userId 비교 후 자기만 삭제]
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody PostDeleteRequest req) {
        postService.delete(id, req);
        return ResponseEntity.noContent().build();
    }
    // 내가 쓴 글 가져오기
    @GetMapping("/post/my")
    public ResponseEntity<List<PostResponse>> findByUserId (@RequestParam Long userId) {

        return ResponseEntity.ok(postService.findByUserId(userId));
    }
 }
