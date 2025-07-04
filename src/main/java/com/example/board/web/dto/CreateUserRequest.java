package com.example.board.web.dto;

import lombok.Builder;

@Builder
public record CreateUserRequest(String username, String password) {

}
