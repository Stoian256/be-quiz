package com.example.bequiz.service;

import com.example.bequiz.domain.Tag;
import com.example.bequiz.exception.EntityValidationException;
import com.example.bequiz.exception.ErrorCode;
import com.example.bequiz.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagProcessorImpl implements TagProcessor {

    private final TagRepository tagRepository;

    @Transactional
    @Override
    public List<Tag> processTags(List<String> tagList) {
        if (tagList != null) {
            return tagList.stream()
                    .map(string -> {
                        String trimmedString = string.trim();
                        if (trimmedString.length() > 1) {
                            return trimmedString.substring(0, 1).toUpperCase() + trimmedString.substring(1).toLowerCase();
                        } else {
                            return trimmedString;
                        }
                    })
                    .map(tagTitle -> {
                        Tag existingTag = tagRepository.findByTagTitle(tagTitle);
                        if (existingTag == null) {
                            existingTag = new Tag(tagTitle);
                            tagRepository.save(existingTag);
                        }
                        return existingTag;
                    }).collect(Collectors.toList());
        }
        return new ArrayList<Tag>();
    }

    @Override
    public List<Tag> getTagsFromTagsAsString(List<String> tagsAsString) {
        if (tagsAsString == null)
            return null;

        return tagsAsString.stream()
                .map(tagTitle -> tagRepository.findOptionalByTagTitleIgnoreCase(tagTitle)
                        .orElseThrow(() -> new EntityValidationException(ErrorCode.NOT_FOUND, "Tag " + tagTitle)))
                .collect(Collectors.toList());
    }
}

