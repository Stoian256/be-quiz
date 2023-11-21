package com.example.bequiz.service;

import com.example.bequiz.dto.TagDTO;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.EntitiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final EntitiesMapper entitiesMapper;

    public List<TagDTO> findByPrefix(String prefix) {
        return tagRepository.findByTagTitleStartingWithIgnoreCaseOrderByTagTitleAsc(prefix, PageRequest.of(0, 5))
                .stream().map(entitiesMapper::tagToTagDTO).toList();
    }
}
