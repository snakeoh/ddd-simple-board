package com.example.board.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.board.domain.vo.AuthorName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @Column(name = "post_id", nullable = false, updatable = false)
    private UUID postId;

    @Embedded
    private AuthorName authorName;

    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post(AuthorName authorName, String title, String content) {
        this.postId = UUID.randomUUID();
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
}
