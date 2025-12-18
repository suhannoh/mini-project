package com.example.backend.controller;

import com.example.backend.dto.LinksRequest;
import com.example.backend.dto.LinksResponse;
import com.example.backend.service.LinksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class LinkController {

    private final LinksService linksService;
    // 유저 링크 추가
    @PostMapping("/links")
    public ResponseEntity<Void> Links (@RequestBody LinksRequest req) {
        linksService.addLinks(req);
        // create / 201
        return ResponseEntity.status(201).build();
    }
    // 유저 링크 수정
    @PutMapping("/links")
    public ResponseEntity<Void> setMyLink (@RequestBody LinksRequest req) {
        linksService.setMyLink(req);
        // ok / 200
        return ResponseEntity.ok().build();
    }
    // 링크 테이블 전체 출력
    @GetMapping("/links")
    public List<LinksResponse> findAllLinks () {
        return linksService.findAllLinks();
    }

    // Health Check !
    @GetMapping("/links/health")
    public ResponseEntity<?> healthCheck() {
        // ok / 200
        return ResponseEntity.ok().build();
    }
}
