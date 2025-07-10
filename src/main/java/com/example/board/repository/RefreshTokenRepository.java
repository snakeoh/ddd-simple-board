package com.example.board.repository;

import com.example.board.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    // This repository interface extends JpaRepository to provide CRUD operations
    // for RefreshToken entities, identified by the username.
    // No additional methods are needed as JpaRepository provides all necessary
    // methods.

}
