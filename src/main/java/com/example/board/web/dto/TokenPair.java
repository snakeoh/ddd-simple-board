package com.example.board.web.dto;

import lombok.Builder;

@Builder
public record TokenPair(String accessToken, String refreshToken) {

}
