package com.example.bequiz.service;

import com.example.bequiz.domain.Tag;
import jakarta.transaction.Transactional;
import java.util.List;

public interface TagProcessor {

    @Transactional
     List<Tag> processTags(List<String> tagList);
}
