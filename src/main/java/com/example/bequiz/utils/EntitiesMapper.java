package com.example.bequiz.utils;

import com.example.bequiz.domain.Question;
import com.example.bequiz.domain.Tag;
import com.example.bequiz.dto.QuestionDTO;
import com.example.bequiz.dto.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntitiesMapper {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagListToTagDTOList")
    QuestionDTO questionToQuestionDTO(Question question);
    @Named("tagListToTagDTOList")
    List<TagDTO> tagListToTagDTOList(List<Tag> tags);
    TagDTO tagToTagDTO(Tag tag);
}