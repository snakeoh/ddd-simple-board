package com.example.board.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record CommentResponse(
        UUID commentId,
        String authorName,
        String content,
        LocalDateTime createdAt) {
}
