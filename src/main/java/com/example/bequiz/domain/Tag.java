package com.example.bequiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
@EntityListeners(AuditingEntityListener.class)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String tagTitle;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<Question> questions;

    @CreatedDate
    private LocalDateTime dateCreated;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime dateLastModified;

    @LastModifiedBy
    private String lastModifiedBy;

    private boolean isDeleted;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagTitle='" + tagTitle + '\'' +
                ", dateCreated=" + dateCreated +
                ", createdBy='" + createdBy + '\'' +
                ", dateLastModified=" + dateLastModified +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
