package com.example.board.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
public class AdminPostController {

    private final PostService postService;

    @PatchMapping("/{postId}/hide")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> hidePost(@PathVariable UUID postId) {
        postService.hidePost(postId);
        return ResponseEntity.noContent().build();
    }
}
