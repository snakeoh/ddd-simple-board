package com.example.board.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.PostRepository;
import com.example.board.domain.vo.AuthorName;
import com.example.board.exception.PostNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post createPost(AuthorName authorName, String title, String content) {
        Post post = new Post(authorName, title, content);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(UUID postId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        post.update(title, content);
        return post;
    }

    @Override
    public void deletePost(UUID postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Post getPost(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public void hidePost(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        post.hide();
    }

}
