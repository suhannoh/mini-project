package com.example.backend.post_comment.service;

import com.example.backend.post_comment.domain.Comment;
import com.example.backend.post.domain.Post;
import com.example.backend.post_comment.dto.read.MyCommentResponse;
import com.example.backend.user.domain.User;
import com.example.backend.post_comment.dto.create.CommentRequest;
import com.example.backend.post_comment.dto.read.CommentResponse;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.post_comment.repository.CommentRepository;
import com.example.backend.post.repository.PostRepository;
import com.example.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    public void newComment (CommentRequest req) {
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

    @Transactional
    public List<CommentResponse> getComment (Long postId) {
        if(postId == null) {
            throw new IllegalArgumentException("POST_ID 받지 못 했습니다 ");
        }
        return commentRepository.findCommentResponsesByPostId(postId);
    }

    public void delete (Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment_ID 에 해당하는 comment가 없습니다"));
        commentRepository.delete(comment);
    }

    public List<MyCommentResponse> getMyComment (Long userId) {
        return commentRepository.findByUserId(userId).stream()
                .map(MyCommentResponse :: create).toList();

    }
}
