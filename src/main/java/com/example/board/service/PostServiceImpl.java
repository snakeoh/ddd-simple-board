package com.example.board.service;

import java.util.List;
import java.util.UUID;

import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.PostRepository;
import com.example.board.domain.vo.AuthorName;
import com.example.board.exception.PostNotFoundException;
import com.example.board.exception.UnauthorizedAccessException;
import com.example.board.web.dto.PostResponse;
import com.example.board.web.dto.UpdatePostRequest;

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

    // @Override
    // public Post updatePost(UUID postId, String title, String content) {
    // Post post = postRepository.findById(postId)
    // .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    // post.update(title, content);
    // return post;
    // }
    @Override
    public PostResponse updatePost(UUID postId, String currentUsername, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.getAuthorName().getValue().equals(currentUsername)) {
            throw new UnauthorizedAccessException("작성자만 게시글을 수정할 수 있습니다.");
        }

        post.update(request.getTitle(), request.getContent());
        return PostResponse.from(post);
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

    public List<Post> getVisiblePosts() {
        return postRepository.findByVisibleTrueOrderByCreatedAtDesc();
    }

}
