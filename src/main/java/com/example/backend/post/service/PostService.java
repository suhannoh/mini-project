package com.example.backend.post.service;

import com.example.backend.post.domain.Post;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void newPost (PostCreateRequest req) {
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
    @Transactional
    public void edit (PostUpdateRequest req) {
        if(req.userId() == null) {
            throw new IllegalArgumentException("USER_ID 비어있습니다. ");
        }
        // 하나씩 나눠서 메세지를 다르게 하면 더 좋을듯
        if( (req.title() == null || req.title().isBlank()) ||
                (req.content() == null || req.content().isBlank()) ||
                (req.category() == null || req.category().isBlank()) ||
                (req.author() == null || req.author().isBlank())) {
            throw new IllegalArgumentException("POST 필수 항목이 비어있습니다 ");
        }
        // 권한 확인 / 403 error - 아직은 못 함

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

    public Post getPostDetail (Long id) {
        // post_id 검사 / 400 error
        if(id == null) {
            throw new IllegalArgumentException("POST_ID 가 없습니다");
        }

        // 없는 게시글 상세 페이지 요청 / 404 error
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

    // 카테고리 이동시 게시글 불러오기
    public Page<PostResponse> findAllPost(String category , Pageable pageable){

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
    public Page<PostResponse> search (PostSearchEnum type , String text , String category , Pageable pageable) {
        if(text == null) {
            throw new IllegalArgumentException("검색할 내용을 입력해주세요");
        }
        // category null이면 all로 처리
        String categoryValue = (category == null || category.isBlank()) ? "all" : category;
        Page<Post> page;
        // true 면 all , false 면 특정 카테고리
        boolean isAll = "all".equalsIgnoreCase(categoryValue);

        // 검색 타입이 제목
        if (type == PostSearchEnum.title) {
            page = isAll // 카테고리 전체 + 제목
                    ? postRepository.findByTitleContainingIgnoreCase(text, pageable)
                    : postRepository.findByCategoryAndTitleContainingIgnoreCase(categoryValue, text, pageable);

        // 검색 타입이 내용
        } else if (type == PostSearchEnum.content) {
            page = isAll // 카테고리 전체 + 내용
                    ? postRepository.findByContentContainingIgnoreCase(text, pageable)
                    : postRepository.findByCategoryAndContentContainingIgnoreCase(categoryValue, text, pageable);

            // 검색 타입이 작성자
        } else {
            page = isAll // 카테고리 전체 + 작성자
                    ? postRepository.findByAuthorContainingIgnoreCase(text, pageable)
                    : postRepository.findByCategoryAndAuthorContainingIgnoreCase(categoryValue, text, pageable);
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
    public void delete(Long id, PostDeleteRequest req) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        if (!post.getUserId().equals(req.userId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}
