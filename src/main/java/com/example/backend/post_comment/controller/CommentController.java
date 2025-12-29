package com.example.backend.post_comment.controller;

import com.example.backend.post_comment.dto.create.CommentRequest;
import com.example.backend.post_comment.dto.read.CommentResponse;
import com.example.backend.post_comment.dto.read.MyCommentResponse;
import com.example.backend.post_comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/comment")
    public ResponseEntity<Void> createComment (@Valid @RequestBody CommentRequest request) {
        commentService.createComment(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/post/{postId}/comment")
    public List<CommentResponse> getComment (@PathVariable Long postId) {
       return commentService.getComment(postId);
    }

    @GetMapping("/post/my/comment")
    public ResponseEntity<List<MyCommentResponse>> getMyComment (@RequestParam Long userId) {
        return ResponseEntity.ok(commentService.getMyComment(userId));
    }

    //Comment ID
    @DeleteMapping("/post/comment/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
