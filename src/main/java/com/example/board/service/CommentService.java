package com.example.board.service;

import java.util.List;
import java.util.UUID;

import com.example.board.domain.entity.Comment;
import com.example.board.domain.vo.AuthorName;

public interface CommentService {
    Comment createComment(UUID postId, AuthorName authorName, String content);

    void deleteComment(UUID commentId);

    void delete(UUID commentId);

    Comment getComment(UUID commentId);

    List<Comment> getCommentsByPostId(UUID postId);

    // Comment updateComment(UUID commentId, String newContent);
    Comment updateComment(UUID commentId, String currentUsername, String newContent);
}
