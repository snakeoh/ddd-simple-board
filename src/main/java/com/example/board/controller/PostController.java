package com.example.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.entity.Post;
import com.example.board.domain.vo.AuthorName;
import com.example.board.service.PostService;
import com.example.board.web.dto.PostCreateRequest;
import com.example.board.web.dto.PostResponse;
import com.example.board.web.dto.PostUpdateRequest;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostCreateRequest request) {
        Post post = postService.createPost(new AuthorName(request.getAuthorName()), request.getTitle(),
                request.getContent());
        return ResponseEntity.status(201).body(toResponse(post));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAll() {
        List<Post> posts = postService.getAllPosts();
        List<PostResponse> responses = posts.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> get(@PathVariable UUID postId) {
        Post post = postService.getPost(postId);
        return ResponseEntity.ok(toResponse(post));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> update(@PathVariable UUID postId, @RequestBody PostUpdateRequest request) {
        Post post = postService.updatePost(postId, request.getTitle(), request.getContent());
        return ResponseEntity.ok(toResponse(post));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    private PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .authorName(post.getAuthorName().getValue())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // PostResponse가 record로 정의 되어 있다면 아래와 같이 작성할 수 있습니다.
    // private PostResponse toResponse(Post post) {
    // return new PostResponse(
    // post.getPostId(),
    // post.getAuthorName().getValue(),
    // post.getTitle(),
    // post.getContent(),
    // post.getCreatedAt(),
    // post.getUpdatedAt()
    // );
    // }
}
