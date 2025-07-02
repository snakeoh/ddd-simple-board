package com.example.board.exception;

import java.util.UUID;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(UUID postId) {
        super("게시글을 찾을 수 없습니다. ID: " + postId);
    }
}
