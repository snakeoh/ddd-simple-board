package com.example.board.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.board.domain.entity.Post;
import com.example.board.domain.enums.PostStatus;

public interface PostRepository {

    Optional<Post> findById(UUID postId);

    Post save(Post post);

    void deleteById(UUID postId);

    List<Post> findAll();

    List<Post> findAllByStatus(PostStatus status);

    Optional<Post> findByPostIdAndStatus(UUID postId, PostStatus status);

    List<Post> findByVisibleTrueOrderByCreatedAtDesc();
}
