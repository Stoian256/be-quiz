package com.example.bequiz.controller;

import com.example.bequiz.dto.TagDTO;
import com.example.bequiz.service.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
@SecurityRequirement(name = "be_quiz_auth")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagDTO> findAll(@RequestParam(required = false) String prefix) {
        return tagService.findByPrefix(prefix);
    }
}
