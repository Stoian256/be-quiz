package com.example.bequiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tag")
public class Tag extends BaseEntity {

    private String tagTitle;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "tags")
    private List<Question> questions;
}
