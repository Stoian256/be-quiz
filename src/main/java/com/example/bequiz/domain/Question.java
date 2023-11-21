package com.example.bequiz.domain;

import com.example.bequiz.utils.Difficulty;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@QueryEntity
@Table(name = "question")
public class Question extends BaseEntity {
    private String questionTitle;

    private String questionBody;

    private Difficulty difficultly;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToMany
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
