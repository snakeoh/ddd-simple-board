package com.example.board.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.board.domain.entity.Comment;
import com.example.board.domain.repository.CommentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findByPostId(UUID postId) {
        String jpql = "SELECT c FROM Comment c WHERE c.postId = :postId ORDER BY c.createdAt DESC";
        TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
        query.setParameter("postId", postId);
        return query.getResultList();
    }

    @Override
    public void save(Comment comment) {
        em.persist(comment);
    }

    @Override
    public void deleteById(UUID commentId) {
        Comment comment = em.find(Comment.class, commentId);
        if (comment != null) {
            em.remove(comment);
        }
    }

}
