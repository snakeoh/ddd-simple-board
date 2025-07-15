package com.example.board.controller;

import com.example.board.domain.entity.Comment;
import com.example.board.domain.vo.AuthorName;
import com.example.board.security.UserPrincipal;
import com.example.board.service.CommentService;
import com.example.board.web.dto.CommentResponse;
import com.example.board.web.dto.CreateCommentRequest;
import com.example.board.web.dto.UpdateCommentRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// import static com.example.board.web.dto.CommentResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable UUID postId,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal UserPrincipal user) {

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    // @DeleteMapping("/comments/{commentId}")
    // public ResponseEntity<Void> delete(@PathVariable UUID commentId) {
    // commentService.delete(commentId);
    // return ResponseEntity.noContent().build();
    // }

    // @PutMapping("/comments/{commentId}")
    // public ResponseEntity<CommentResponse> updateComment(
    // @PathVariable UUID commentId,
    // @Valid @RequestBody UpdateCommentRequest request) {
    // Comment update = commentService.updateComment(
    // commentId,
    // request.getContent());
    // return ResponseEntity.ok(CommentResponse.from(update));
    // }
    @PutMapping("/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable UUID commentId,
            @Valid @RequestBody UpdateCommentRequest request) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment update = commentService.updateComment(commentId, currentUsername, request.getContent());
        return ResponseEntity.ok(CommentResponse.from(update));
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
