package com.example.board.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostRequest {
    private String authorName;
    private String title;
    private String content;

}
