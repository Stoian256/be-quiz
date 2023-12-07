package com.example.bequiz.controller;

import com.example.bequiz.domain.TagFilters;
import com.example.bequiz.dto.TagDTO;
import com.example.bequiz.service.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
@SecurityRequirement(name = "be_quiz_auth")
public class TagController {
    private final TagService tagService;

    @PostMapping
    public List<TagDTO> findAll(@RequestBody(required = false) TagFilters tagFilters) {
        return tagService.findAllWithPrefixAndExcludedTags(tagFilters);
    }

    @GetMapping("/top")
    public List<TagDTO> findMostPopularTagsForQuizzes(@RequestParam(required = false) String query, @RequestParam(required = false) List<String> excludedTags, @RequestParam int numberOfResults){
       return  tagService.findMostPopularQuizTags(query,excludedTags,numberOfResults);
    }
 }
