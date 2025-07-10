package com.example.board.web.dto;

import java.util.UUID;

import com.example.board.domain.enums.Role;

public record MeResponse(
        UUID userId,
        String username,
        Role role) {

}
