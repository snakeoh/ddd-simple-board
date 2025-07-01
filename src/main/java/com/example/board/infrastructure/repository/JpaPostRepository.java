package com.example.board.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.PostRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class JpaPostRepository implements PostRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Post> findById(UUID postId) {
        return Optional.ofNullable(em.find(Post.class, postId));
    }

    @Override
    public void save(Post post) {
        em.persist(post);
    }

    @Override
    public void deleteById(UUID postId) {
        findById(postId).ifPresent(em::remove);
    }
}
