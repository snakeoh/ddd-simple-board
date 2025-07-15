package com.example.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.board.domain.entity.RefreshToken;
import com.example.board.domain.entity.User;
import com.example.board.repository.RefreshTokenRepository;
import com.example.board.repository.UserRepository;
import com.example.board.security.JwtTokenProvider;
import com.example.board.service.UserService;
import com.example.board.web.dto.CreateUserRequest;
import com.example.board.web.dto.LoginRequest;
import com.example.board.web.dto.MeResponse;
import com.example.board.web.dto.RefreshRequest;
import com.example.board.web.dto.TokenPair;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody CreateUserRequest request) {
        userService.signup(request);
        return ResponseEntity.ok().build();
    }

    // @PostMapping("/login")
    // public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)
    // {
    // String token = userService.login(request);
    // return ResponseEntity.ok(new LoginResponse(token));
    // }
    @PostMapping("/login")
    public ResponseEntity<TokenPair> login(@RequestBody LoginRequest request) {
        TokenPair token = userService.loginAndGetTokens(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenPair> refresh(@RequestBody RefreshRequest request) {
        String username = jwtProvider.extractUsername(request.refreshToken());

        RefreshToken saved = refreshTokenRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (!saved.getRefreshToken().equals(request.refreshToken())) {
            throw new RuntimeException("Refresh token does not match");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String newAccess = jwtProvider.generateToken(username, user.getRole().name());
        String newRefresh = jwtProvider.generateRefreshToken(username);

        refreshTokenRepository.save(new RefreshToken(username, newRefresh));

        return ResponseEntity.ok(new TokenPair(newAccess, newRefresh));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MeResponse> getMyInfo(@AuthenticationPrincipal String username) {
        MeResponse info = userService.getMyInfo(username);
        return ResponseEntity.ok(info);
    }

}
