package com.example.backend.service;

import com.example.backend.domain.Post;
import com.example.backend.domain.User;
import com.example.backend.dto.PostRequestDto;
import com.example.backend.dto.PostResponseDto;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void newPost (PostRequestDto req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Post post = Post.create(req);
        postRepository.save(post);
    }

    public List<PostResponseDto> findAllPost() {
        List<Post> list_all = postRepository.findAll(
                Sort.by(Sort.Direction.DESC, "id")
        );
        List<PostResponseDto> list = new ArrayList<>();
        for(Post li : list_all) {
            list.add(new PostResponseDto(
                    li.getId(), li.getTitle() , li.getContent(),
                    li.getCategory() , li.getAuthor()
            ));
        }
        return list;
    }

    public Post getPostDetail (Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
    }
}
