package com.example.board.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.board.domain.entity.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
    private UUID postId;
    private String authorName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse from(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'from'");
    }
}
