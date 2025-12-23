package com.example.backend.link.controller;

import com.example.backend.link.dto.create.LinksRequest;
import com.example.backend.link.dto.read.LinksResponse;
import com.example.backend.link.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;
    // 유저 링크 추가
    @PostMapping("/link")
    public ResponseEntity<Void> Links (@RequestBody LinksRequest req) {
        linkService.addLinks(req);
        // create / 201
        return ResponseEntity.status(201).build();
    }
    // 유저 링크 수정
    @PutMapping("/link")
    public ResponseEntity<Void> setMyLink (@RequestBody LinksRequest req) {
        linkService.setMyLink(req);
        // ok / 200
        return ResponseEntity.ok().build();
    }
    // 링크 테이블 전체 출력
    @GetMapping("/link")
    public List<LinksResponse> findAllLinks () {
        return linkService.findAllLinks();
    }

    // Health Check !
    @GetMapping("/link/health")
    public ResponseEntity<?> healthCheck() {
        // ok / 200
        return ResponseEntity.ok().build();
    }
}
