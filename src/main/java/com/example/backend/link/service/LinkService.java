package com.example.backend.link.service;

import com.example.backend.link.domain.Link;
import com.example.backend.user.domain.User;
import com.example.backend.link.dto.create.LinksRequest;
import com.example.backend.link.dto.read.LinksResponse;
import com.example.backend.common.error.BusinessException;
import com.example.backend.common.error.ErrorCode;
import com.example.backend.link.repository.LinkRepository;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public void addLinks (LinksRequest req) {
        if(req.getUserId() == null) {
            // USER_ID 검사 / 400 error
            throw new IllegalArgumentException("USER_ID 가 비어있습니다");
        }
        Link link = new Link(
                req.getNotionUrl(),
                req.getGitHubUrl(),
                req.getUserId()
        );
        // 저장 / 저장 중 에러는 etc 예외로 통일
        linkRepository.save(link);
    }

    public void setMyLink (LinksRequest req) {
        Link link = linkRepository.findByUserId(req.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.LINK_NOT_FOUND));;

        link.setGitHubUrl(req.getGitHubUrl());
        link.setNotionUrl(req.getNotionUrl());
        // 수정 저장 / 저장 중 에러는 etc 예외로 통일
        linkRepository.save(link);
    }

    public List<LinksResponse> findAllLinks () {
        List<Link> list = linkRepository.findAll();
        List<LinksResponse> resList = new ArrayList<>();
        for(Link l : list) {
            User user = userRepository.findUserById(l.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

            resList.add(new LinksResponse(l,user.getName(), user.getGender()));
        }
        return resList;
    }
}
