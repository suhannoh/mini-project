package com.example.backend.service;

import com.example.backend.domain.Links;
import com.example.backend.domain.User;
import com.example.backend.dto.LinksRequest;
import com.example.backend.dto.LinksResponse;
import com.example.backend.error.BusinessException;
import com.example.backend.error.ErrorCode;
import com.example.backend.repository.LinksRepository;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LinksService {

    private final LinksRepository linksRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public void addLinks (LinksRequest req) {
        if(req.getUserId() == null) {
            // USER_ID 검사 / 400 error
            throw new IllegalArgumentException("USER_ID 가 비어있습니다");
        }

        Links links = new Links (
                req.getNotionUrl(),
                req.getGitHubUrl(),
                req.getUserId()
        );
        // 저장 / 저장 중 에러는 etc 예외로 통일
        linksRepository.save(links);
    }

    public void setMyLink (LinksRequest req) {
        Links link = linksRepository.findByUserId(req.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.LINK_NOT_FOUND));;

        link.setGitHubUrl(req.getGitHubUrl());
        link.setNotionUrl(req.getNotionUrl());
        // 수정 저장 / 저장 중 에러는 etc 예외로 통일
        linksRepository.save(link);
    }

    public List<LinksResponse> findAllLinks () {
        List<Links> list = linksRepository.findAll();
        List<LinksResponse> resList = new ArrayList<>();
        for(Links l : list) {
            User user = userRepository.findUserById(l.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

            resList.add(new LinksResponse(l,user.getName()));
        }
        return resList;
    }
}
