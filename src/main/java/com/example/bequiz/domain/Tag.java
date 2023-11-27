package com.example.bequiz.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;


@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

    private String tagTitle;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "tags")
    private List<Question> questions;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "quizTags")
    private List<Quiz> quizzes;

    public Tag(String tagTitle) {
        this.tagTitle = tagTitle;
    }
}
