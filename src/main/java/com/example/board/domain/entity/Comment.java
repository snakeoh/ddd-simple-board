package com.example.board.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.board.domain.vo.AuthorName;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @Column(name = "comment_id", nullable = false, updatable = false)
    private UUID commentId;

    @Embedded
    private AuthorName authorName;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(AuthorName authorName, String content) {
        this.commentId = UUID.randomUUID();
        this.authorName = authorName;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    protected void setPost(Post post) {
        this.post = post;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
