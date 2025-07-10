package com.example.board.web.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String token) {

}
