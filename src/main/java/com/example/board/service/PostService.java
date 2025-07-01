package com.example.board.service;

import java.util.List;
import java.util.UUID;

import com.example.board.domain.entity.Post;
import com.example.board.domain.vo.AuthorName;

public interface PostService {
    Post createPost(AuthorName authorName, String title, String content);

    Post updatePost(UUID postId, String title, String content);

    void deletePost(UUID postId);

    Post getPost(UUID postId);

    List<Post> getAllPosts();
}
