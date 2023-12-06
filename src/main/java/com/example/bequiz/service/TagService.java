package com.example.bequiz.service;

import com.example.bequiz.domain.TagFilters;
import com.example.bequiz.dto.TagDTO;
import com.example.bequiz.repository.TagRepository;
import com.example.bequiz.utils.EntitiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final EntitiesMapper entitiesMapper;

    public List<TagDTO> findAllWithPrefixAndExcludedTags(TagFilters tagFilters) {
        if (tagFilters != null && tagFilters.getPrefix() != null && tagFilters.getExcludedTags() != null)
            return tagRepository.findByTagTitleStartingWithIgnoreCaseAndTagTitleNotInIgnoreCaseOrderByTagTitleAsc(
                            tagFilters.getPrefix(), tagFilters.getExcludedTags(), PageRequest.of(0, 7))
                    .stream()
                    .map(entitiesMapper::tagToTagDTO)
                    .toList();
        return tagRepository.findAll().stream()
                .map(entitiesMapper::tagToTagDTO)
                .toList();
    }

    public List<TagDTO> findMostPopularQuizTags(String query, List<String> excludedTags,int numberOfResults) {
       return entitiesMapper.tagListToTagDTOList(tagRepository.findTopPopularTags(query,excludedTags).stream().limit(numberOfResults).collect(Collectors.toList()));
    }
}
