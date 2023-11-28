package com.example.bequiz.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true,exclude = {"questions","quizzes"})
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

    private String tagTitle;

    @ToString.Exclude
    @ManyToMany(mappedBy = "tags")
    private List<Question> questions;

    @ToString.Exclude
    @ManyToMany(mappedBy = "quizTags")
    private List<Quiz> quizzes;

    public Tag(String tagTitle) {
        this.tagTitle = tagTitle;
    }
}
