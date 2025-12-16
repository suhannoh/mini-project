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
    //links 저장
    @PostMapping("/links")
    public void Links (@RequestBody LinksRequest req) {
        try {
            linksService.addLinks(req);
        } catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/links")
    public void setMyLink (@RequestBody LinksRequest req) {
        linksService.setMyLink(req);
    }

    @GetMapping("/links")
    public List<LinksResponse> findAllLinks () {
        return linksService.findAllLinks();
    }
        @GetMapping("/links/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
