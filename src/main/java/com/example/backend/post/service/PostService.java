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

    public List<PostResponse> findAllPost(String category) {
        // post_id를 기준으로 내림차순하여 가져옴
//        List<Post> list_all = postRepository.findAll(
//                Sort.by(Sort.Direction.DESC, "id")
//        );
        String cat = (category == null || category.isBlank()) ? "all" : category;
        List<Post> list_all;
        if("all".equals(cat)) {
            list_all = postRepository.findAll(
                    Sort.by(Sort.Direction.DESC, "id")
            );
        } else {
            list_all = postRepository.findByCategoryOrderByIdDesc(category);
        }

        List<PostResponse> list = new ArrayList<>();

        for(Post li : list_all) {
            list.add(new PostResponse(
                    li.getId(), li.getTitle() , li.getContent(),
                    li.getCategory() , li.getAuthor(), li.getCreatedAt()
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

    public List<PostResponse> search (PostSearchEnum type , String text , String category) {
        if(text == null) {
            throw new IllegalArgumentException("검색할 내용을 입력해주세요");
        }
        // category null이면 all로 처리
        String cat = (category == null || category.isBlank()) ? "all" : category;

        List<Post> list_all = switch (type) {
            case title -> postRepository.findByTitleContainingIgnoreCaseOrderByIdDesc(text);
            case content -> postRepository.findByContentContainingIgnoreCaseOrderByIdDesc(text);
            case author -> postRepository.findByAuthorContainingIgnoreCaseOrderByIdDesc(text);
        };
        List<Post> posts = new ArrayList<>();

        if("all".equals(cat)) {
            posts = list_all;
        } else {
            for(Post p : list_all) {
                if (cat.equals(p.getCategory())) {
                    posts.add(p);
                }
            }
        }
        List<PostResponse> result = new ArrayList<>();

        for(Post p : posts) {
            result.add(new PostResponse(
                    p.getId(), p.getTitle() , p.getContent(),
                    p.getCategory() , p.getAuthor() , p.getCreatedAt()
            ));
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
