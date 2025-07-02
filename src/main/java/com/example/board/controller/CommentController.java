package com.example.board.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.entity.Comment;
import com.example.board.domain.vo.AuthorName;
import com.example.board.service.CommentService;
import com.example.board.web.dto.CommentResponse;
import com.example.board.web.dto.CreateCommentRequest;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable UUID postId,
            @RequestBody CreateCommentRequest request) {

        Comment comment = commentService.createComment(
                postId,
                new AuthorName(request.authorName()),
                request.content());
        return ResponseEntity.ok(toResponse(comment));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable UUID commentId) {
        Comment comment = commentService.getComment(commentId);
        return ResponseEntity.ok(toResponse(comment));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable UUID postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<CommentResponse> responses = comments.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .authorName(comment.getAuthorName().getValue())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}
