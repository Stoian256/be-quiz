package com.example.bequiz.repository;

import com.example.bequiz.domain.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    Tag findByTagTitle(String tagTitle);

    List<Tag> findByTagTitleStartingWithIgnoreCaseAndTagTitleNotInIgnoreCaseOrderByTagTitleAsc(String searchString, List<String> excludedTags, PageRequest of);

    Optional<Tag> findOptionalByTagTitleIgnoreCase(String tagTitle);

    @Query(value = "SELECT t.* FROM Tag t " +
            "INNER JOIN quiz_tag qt ON t.id = qt.tag_id " +
            "WHERE (:query IS NULL OR t.tag_title LIKE %:query%) " +
            "AND (COALESCE(:excludeTags) IS NULL OR t.tag_title NOT IN :excludeTags) " +
            "GROUP BY t.id " +
            "ORDER BY COUNT(qt.tag_id) DESC",
    nativeQuery = true)
    List<Tag> findTopPopularTags(@Param("query") String query,
                                 @Param("excludeTags") List<String> excludeTags);
}

