package com.example.board.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.board.domain.entity.Comment;

import lombok.Builder;

@Builder
public record CommentResponse(
        UUID commentId,
        String authorName,
        String content,
        LocalDateTime createdAt) {

    // 두 가지 방법이 있다.
    // 1. Builder 패턴을 사용하여 생성
    // public static CommentResponse from(Comment comment) {
    // return CommentResponse.builder()
    // .commentId(comment.getCommentId())
    // .authorName(comment.getAuthorName().getValue())
    // .content(comment.getContent())
    // .createdAt(comment.getCreatedAt())
    // .build();
    // }
    // 2. 생성자를 사용하여 생성
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getAuthorName().getValue(),
                comment.getContent(),
                comment.getCreatedAt());
    }
}