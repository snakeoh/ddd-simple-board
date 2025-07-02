package com.example.board.web.dto;

import lombok.Builder;

@Builder
public record CreateCommentRequest(String authorName, String content) {
}
