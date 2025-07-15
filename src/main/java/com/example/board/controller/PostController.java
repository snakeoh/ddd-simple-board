package com.example.board.controller;

import com.example.board.domain.entity.Post;
import com.example.board.domain.vo.AuthorName;
import com.example.board.service.PostService;
import com.example.board.web.dto.CreatePostRequest;
import com.example.board.web.dto.PostResponse;
import com.example.board.web.dto.UpdatePostRequest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "게시글 API", description = "게시글 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody CreatePostRequest request) {
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
    @PreAuthorize("#author == authentication.name or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PostResponse> update(@PathVariable UUID postId, @RequestBody UpdatePostRequest request,
            @RequestParam String author) throws AccessDeniedException {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        // PostResponse updated = postService.updatePost(postId, request.getTitle(),
        // request.getContent());
        Post post = postService.getPost(postId);
        if (!post.getAuthorName().equals(currentUsername)) {
            throw new AccessDeniedException("작성사 본인만 수정할 수 있습니다.");
        }
        PostResponse updated = postService.updatePost(postId, currentUsername, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> adminDeletePost() {
        return null;
    }

    @PatchMapping("/{postId}/hide")
    public ResponseEntity<Void> hidePost(@PathVariable UUID postId) {
        postService.hidePost(postId);
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
