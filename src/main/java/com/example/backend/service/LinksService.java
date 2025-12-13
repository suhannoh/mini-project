package com.example.backend.service;

import com.example.backend.domain.Links;
import com.example.backend.domain.User;
import com.example.backend.dto.LinksRequest;
import com.example.backend.dto.LinksResponse;
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

        // 입력값 검증

        Links links = new Links (
                req.getNotionUrl(),
                req.getGitHubUrl(),
                req.getUserId()
        );

        linksRepository.save(links);
    }

    public void setMyLink (LinksRequest req) {
        Links link = linksRepository.findByUserId(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("링크가 존재하지 않습니다."));;

        link.setGitHubUrl(req.getGitHubUrl());
        link.setNotionUrl(req.getNotionUrl());
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
