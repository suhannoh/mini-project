package com.example.backend.service;

import com.example.backend.domain.Comment;
import com.example.backend.domain.Post;
import com.example.backend.domain.User;
import com.example.backend.dto.comments.CommentRequestDto;
import com.example.backend.dto.comments.CommentResponse;
import com.example.backend.error.BusinessException;
import com.example.backend.error.ErrorCode;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void newComment (CommentRequestDto req) {
        //400
        if (req.postId() == null ) {
           throw new BusinessException(ErrorCode.BAD_REQUEST,"POST_ID 가 비어있습니다 ") ;
        }
        if(req.userId() == null) {
            throw new IllegalArgumentException(" UESR_ID 가 비어있습니다 ") ;
        }
        if(req.comment().isBlank()) {
            throw new IllegalArgumentException(" 공백만 입력할 수 없습니다 ") ;
        }

        // 404
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(req.postId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));


        Comment comment = Comment.create(req);
        commentRepository.save(comment);
    }

    public List<CommentResponse> getComment (Long id) {
        List<Comment> list = commentRepository.findByPostIdOrderByIdDesc(id);
        List<CommentResponse> result = new ArrayList<>();
        for(Comment c : list ) {
            User user = userRepository.findById(c.getUserId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
            result.add(CommentResponse.create(c,user.getName()));
        }
        return result;
    }
}
