package com.example.bequiz.utils;

import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.TagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntitiesMapper {
    TagDTO tagToTagDTO(Tag tag);
}