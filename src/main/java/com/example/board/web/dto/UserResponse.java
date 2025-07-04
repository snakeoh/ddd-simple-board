package com.example.board.web.dto;

import java.util.UUID;

import com.example.board.domain.enums.Role;

import lombok.Builder;

@Builder
public record UserResponse(UUID userId, String username, Role role) {

}
