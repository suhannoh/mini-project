package com.example.backend.service;

import com.example.backend.domain.Post;
import com.example.backend.domain.User;
import com.example.backend.dto.PostRequestDto;
import com.example.backend.dto.PostResponseDto;
import com.example.backend.enums.PostSearchEnum;
import com.example.backend.error.BusinessException;
import com.example.backend.error.ErrorCode;
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
        // 유효성 검사 / 400 error
        if(req.getUserId() == null) {
            throw new IllegalArgumentException("USER_ID 비어있습니다. ");
        }
        // 하나씩 나눠서 메세지를 다르게 하면 더 좋을듯
        if( (req.getTitle() == null || req.getTitle().isBlank()) ||
            (req.getContent() == null || req.getContent().isBlank()) ||
            (req.getCategory() == null || req.getCategory().isBlank()) ||
            (req.getAuthor() == null || req.getAuthor().isBlank())) {
            throw new IllegalArgumentException("POST 필수 항목이 비어있습니다 ");
        }
        // 권한 확인 / 403 error - 아직은 못 함

        // 유저 정보 확인 / 404 error
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 저장 / 저장 중 에러는 etc 예외로 통일
        Post post = Post.create(req);
        postRepository.save(post);
    }

    public List<PostResponseDto> findAllPost() {
        // post_id를 기준으로 내림차순하여 가져옴
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
        // post_id 검사 / 400 error
        if(id == null) {
            throw new IllegalArgumentException("POST_ID 가 없습니다");
        }

        // 없는 게시글 상세 페이지 요청 / 404 error
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

    public List<PostResponseDto> search (PostSearchEnum type , String text) {
        if(text == null) {
            throw new IllegalArgumentException("검색할 내용을 입력해주세요");
        }
        List<Post> list_all = switch (type) {
            case title -> postRepository.findByTitleContainingIgnoreCaseOrderByIdDesc(text);
            case content -> postRepository.findByContentContainingIgnoreCaseOrderByIdDesc(text);
            case author -> postRepository.findByAuthorContainingIgnoreCaseOrderByIdDesc(text);
        };

        List<PostResponseDto> list = new ArrayList<>();

        for(Post li : list_all) {
            list.add(new PostResponseDto(
                    li.getId(), li.getTitle() , li.getContent(),
                    li.getCategory() , li.getAuthor()
            ));
        }

        return list;
    }
}
