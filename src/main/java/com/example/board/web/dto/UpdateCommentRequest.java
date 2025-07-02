package com.example.board.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCommentRequest {

    @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
    private String content;

}
