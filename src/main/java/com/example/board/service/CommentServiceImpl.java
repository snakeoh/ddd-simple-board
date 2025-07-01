package com.example.board.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.domain.repository.PostRepository;
import com.example.board.domain.vo.AuthorName;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public Comment createComment(UUID postId, AuthorName authorName, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = new Comment(authorName, content);
        post.addComment(comment);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(UUID commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment getComment(UUID commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }

    @Override
    public List<Comment> getCommentsByPostId(UUID postId) {
        return commentRepository.findByPostId(postId);
    }
}
