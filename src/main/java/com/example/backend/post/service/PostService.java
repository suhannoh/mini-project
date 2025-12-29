package com.example.backend.post.service;

import com.example.backend.post.domain.Post;
import com.example.backend.post.dto.read.PostSearchRequest;
import com.example.backend.user.domain.User;
import com.example.backend.post.dto.delete.PostDeleteRequest;
import com.example.backend.post.dto.update.PostUpdateRequest;
import com.example.backend.post.dto.create.PostCreateRequest;
import com.example.backend.post.dto.read.PostResponse;
import com.example.backend.post.dto.search.PostSearchEnum;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.post.repository.PostRepository;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost (PostCreateRequest req) {
        // 유저 정보 확인 / 404 error
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 저장 / 저장 중 에러는 etc 예외로 통일
        Post post = Post.create(req);
        postRepository.save(post);
    }

    @Transactional
    public void updatePost (PostUpdateRequest req) {
        // 유저 정보 확인 / 404 error
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 저장 / 저장 중 에러는 etc 예외로 통일
        Post post = postRepository.findById(req.postId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        post.setTitle(req.title());
        post.setContent(req.content());
        post.setCategory(req.category());
        post.setAuthor(req.author());

        postRepository.save(post);
    }


    // 카테고리 이동시 게시글 불러오기
    @Transactional(readOnly = true)
    public Page<PostResponse> findByCategory(String category , Pageable pageable){

        String categoryValue = (category == null || category.isBlank()) ? "all" : category;
        Page<Post> page;

        if("all".equals(categoryValue)) {
            // 전체
            page = postRepository.findAll(pageable);
        } else {
            // 카테고리 판별
            page = postRepository.findByCategoryOrderByIdDesc(category , pageable);
        }
            // PostResponse로 전달
        return  page.map(post -> new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getAuthor(),
                post.getCreatedAt()
        ));
    }

    // 게시글 검색 (카테고리 + 검색 타입 + 검색어 )
    @Transactional(readOnly = true)
    public Page<PostResponse> searchPost (PostSearchRequest request, Pageable pageable) {
        // category null이면 all로 처리
        String categoryValue =
                (request.category() == null || request.category().isBlank()) ? "all" : request.category();
        Page<Post> page;
        // true 면 all , false 면 특정 카테고리
        boolean isAll = "all".equalsIgnoreCase(categoryValue);

        // 검색 타입이 제목
        if (request.type() == PostSearchEnum.title) {
            page = isAll // 카테고리 전체 + 제목
                    ? postRepository.findByTitleContainingIgnoreCase(request.text(), pageable)
                    : postRepository.findByCategoryAndTitleContainingIgnoreCase(categoryValue, request.text(), pageable);

            // 검색 타입이 내용
        } else if (request.type() == PostSearchEnum.content) {
            page = isAll // 카테고리 전체 + 내용
                    ? postRepository.findByContentContainingIgnoreCase(request.text(), pageable)
                    : postRepository.findByCategoryAndContentContainingIgnoreCase(categoryValue, request.text(), pageable);

            // 검색 타입이 작성자
        } else {
            page = isAll // 카테고리 전체 + 작성자
                    ? postRepository.findByAuthorContainingIgnoreCase(request.text(), pageable)
                    : postRepository.findByCategoryAndAuthorContainingIgnoreCase(categoryValue, request.text(), pageable);
        }

        return page.map(p -> new PostResponse(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                p.getCategory(),
                p.getAuthor(),
                p.getCreatedAt()
        ));
    }

    @Transactional(readOnly = true)
    public Post getPostDetail (Long id) {
        // post_id 검사 / 400 error
        if(id == null) {
            throw new IllegalArgumentException("POST_ID 가 없습니다");
        }

        // 없는 게시글 상세 페이지 요청 / 404 error
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }


    @Transactional(readOnly = true)
    public List<PostResponse> findAllAnon (String category) {
        List<Post> postByAnon;
        if (category == null || category.isBlank() || category.equals("all")) {
            postByAnon = postRepository.findByAuthor("익명");
        } else {
            postByAnon = postRepository.findByCategoryAndAuthor(category, "익명");
        }
        List<PostResponse> result = new ArrayList<>();
        for(Post p : postByAnon) {
            PostResponse res = new PostResponse(
                    p.getId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getCategory(),
                    p.getAuthor(),
                    p.getCreatedAt()
            );
            result.add(res);
        }
        return result;
    }

    @Transactional
    public void deletePost(Long id, PostDeleteRequest req) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        if (!post.getUserId().equals(req.userId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findByUserId (Long userId) {
        if(userId == null) {
            throw new IllegalArgumentException("USER_ID 가 비어있습니다.. ");
        }
        List<Post> result = postRepository.findByUserId(userId);
        List<PostResponse> resultRes = new ArrayList<>();

        for(Post p : result) {
            PostResponse res = new PostResponse(
                    p.getId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getCategory(),
                    p.getAuthor(),
                    p.getCreatedAt()
            );
            resultRes.add(res);
        }
        return resultRes;
    }
}
