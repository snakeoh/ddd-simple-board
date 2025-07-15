package com.example.board.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByPostPostId(UUID postId);

    Optional<Comment> findById(UUID commentId);

    // The save method is inherited from JpaRepository and does not need to be
    // redeclared.
    // Comment save(Comment comment);

    void deleteById(UUID commentId);
}
