package com.example.backend.post_comment.controller;

import com.example.backend.post_comment.dto.create.CommentRequest;
import com.example.backend.post_comment.dto.read.CommentResponse;
import com.example.backend.post_comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/comment")
    public ResponseEntity<Void> createComment (@RequestBody CommentRequest req) {
        commentService.newComment(req);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/post/{postId}/comment")
    public List<CommentResponse> getComment (@PathVariable Long postId) {
       return commentService.getComment(postId);
    }

    //Comment ID
    @DeleteMapping("/post/comment/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
