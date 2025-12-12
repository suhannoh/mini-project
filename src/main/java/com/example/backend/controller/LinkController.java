package com.example.backend.controller;

import com.example.backend.dto.LinksRequest;
import com.example.backend.dto.LinksResponse;
import com.example.backend.service.LinksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {
    "https://suhannoh.github.io/mini-project-frontend",
    "http://localhost:5173"
})
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

    @GetMapping("/links")
    public List<LinksResponse> findAllLinks () {
        return linksService.findAllLinks();
    }
}
