package com.example.backend.link.service;

import com.example.backend.link.domain.Link;
import com.example.backend.user.domain.User;
import com.example.backend.link.dto.create.LinksRequest;
import com.example.backend.link.dto.read.LinksResponse;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.link.repository.LinkRepository;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    // 회원 링크 테이블
    private final LinkRepository linkRepository;
    // 회원 테이블
    private final UserRepository userRepository;

    @Transactional
    public void createLink (LinksRequest req) {
        Link link = new Link(
                req.getNotionUrl(),
                req.getGitHubUrl(),
                req.getUserId()
        );
        // 저장 / 저장 중 에러는 etc 예외로 통일
        linkRepository.save(link);
    }

    @Transactional
    public void updateLink (LinksRequest req) {
        Link link = linkRepository.findByUserId(req.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.LINK_NOT_FOUND));
        if(!link.getGitHubUrl().equals(req.getGitHubUrl())){
            link.setGitHubUrl(req.getGitHubUrl());
        }
        if(!link.getNotionUrl().equals(req.getNotionUrl())) {
            link.setNotionUrl(req.getNotionUrl());
        }
        // 수정 저장 / 저장 중 에러는 etc 예외로 통일
        linkRepository.save(link);
    }

    @Transactional(readOnly = true)
    public List<LinksResponse> findAllLinks () {
        return linkRepository.findAll().stream()
                .map(link -> {
                    User user = userRepository.findUserById(link.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
                    return new LinksResponse(link, user.getName(), user.getGender());
                }).toList();
    }
}
