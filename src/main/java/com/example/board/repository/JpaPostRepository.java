package com.example.board.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.board.domain.entity.Post;
import com.example.board.domain.enums.PostStatus;
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
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public void deleteById(UUID postId) {
        findById(postId).ifPresent(em::remove);
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    @Override
    public Optional<Post> findByPostIdAndStatus(UUID postId, PostStatus status) {
        List<Post> result = em.createQuery(
                "SELECT p FROM Post p WHERE p.id = :postId AND p.status = :status", Post.class)
                .setParameter("postId", postId)
                .setParameter("status", status)
                .getResultList();
        return result.stream().findFirst();
    }

    @Override
    public List<Post> findAllByStatus(PostStatus status) {
        return em.createQuery(
                "SELECT p FROM Post p WHERE p.status = :status", Post.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<Post> findByVisibleTrueOrderByCreatedAtDesc() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByVisibleTrueOrderByCreatedAtDesc'");
    }

}
