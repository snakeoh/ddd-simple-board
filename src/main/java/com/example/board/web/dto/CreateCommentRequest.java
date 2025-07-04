package com.example.board.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateCommentRequest(
        @NotBlank(message = "작성자 이름은 비어있을 수 없습니다.") String authorName,

        String content) {
}
