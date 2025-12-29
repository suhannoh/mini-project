package com.example.backend.post_like.service;

import com.example.backend.post_like.domain.Like;
import com.example.backend.post_like.domain.LikeStatus;
import com.example.backend.post_like.dto.LikeCreateRequest;
import com.example.backend.post_like.dto.LikeResponse;
import com.example.backend.post_like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public LikeResponse createLike (LikeCreateRequest req) {
        Like like = likeRepository.findByUserIdAndPostId(req.userId(), req.postId());

        boolean liked;

        if(like == null) {
            Like createLike = new Like();
            createLike.setUserId(req.userId());
            createLike.setPostId(req.postId());
            createLike.setStatus(LikeStatus.LIKE);
            likeRepository.save(createLike);
            liked = true;
        } else {
            likeRepository.delete(like);
            liked = false;
        }

        int likeCount = likeRepository.countByPostIdAndStatus(req.postId(), LikeStatus.LIKE);
        return new LikeResponse(likeCount , liked);
    }

    @Transactional(readOnly = true)
    public LikeResponse readLike (LikeCreateRequest req) {
        Like like = likeRepository.findByUserIdAndPostId(req.userId(), req.postId());

        boolean liked = like != null;
        int likeCount = likeRepository.countByPostIdAndStatus(req.postId(), LikeStatus.LIKE);
        return new LikeResponse(likeCount , liked);
    }
}
