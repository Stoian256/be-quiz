package com.example.bequiz.repository;

import com.example.bequiz.domain.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    Tag findByTagTitle(String tagTitle);
    List<Tag> findByTagTitleStartingWithIgnoreCaseOrderByTagTitleAsc(String searchString, PageRequest of);
    List<Tag> findByTagTitleIn(List<String> tagTitles);

}
