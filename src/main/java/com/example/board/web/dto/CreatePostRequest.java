package com.example.board.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostRequest {
    @Schema(description = "작성자 이름", example = "홍길동")
    private String authorName;

    private String title;
    private String content;

}
