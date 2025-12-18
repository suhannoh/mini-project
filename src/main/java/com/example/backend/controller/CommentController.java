package com.example.backend.controller;

import com.example.backend.dto.comments.CommentRequestDto;
import com.example.backend.dto.comments.CommentResponse;
import com.example.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/comment")
    public ResponseEntity<Void> newComment (@RequestBody CommentRequestDto req) {
        commentService.newComment(req);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/post/comment/{id}")
    public List<CommentResponse> getComment (@PathVariable Long id) {
       return commentService.getComment(id);
    }
}
