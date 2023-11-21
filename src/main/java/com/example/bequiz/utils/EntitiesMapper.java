package com.example.bequiz.utils;

import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class EntitiesMapper {
    public TagDTO tagToTagDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .tagTitle(tag.getTagTitle())
                .build();
    }
}