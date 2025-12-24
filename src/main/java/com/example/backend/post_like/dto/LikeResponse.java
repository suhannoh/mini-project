package com.example.backend.post_like.dto;

import com.example.backend.post_like.domain.LikeStatus;

public record LikeResponse(
        int likeCount , boolean liked
) {


}
