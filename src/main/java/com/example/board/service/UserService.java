package com.example.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.entity.RefreshToken;
import com.example.board.domain.entity.User;
import com.example.board.domain.enums.Role;
import com.example.board.repository.RefreshTokenRepository;
import com.example.board.repository.UserRepository;
import com.example.board.security.JwtTokenProvider;
import com.example.board.security.Sha256PasswordEncoder;
import com.example.board.web.dto.CreateUserRequest;
import com.example.board.web.dto.LoginRequest;
import com.example.board.web.dto.MeResponse;
import com.example.board.web.dto.TokenPair;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Sha256PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void signup(CreateUserRequest request) {
        String hashedPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.username(),
                hashedPassword,
                Role.USER);
        // Assuming a default role for new users);
        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String raw = request.password();
        String encoded = user.getPassword();

        if (!passwordEncoder.encode(raw).equals(encoded)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.generateToken(user.getUsername(), user.getRole().name());
    }

    public TokenPair loginAndGetTokens(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.encode(request.password()).equals(user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

        refreshTokenRepository.save(new RefreshToken(user.getUsername(), refreshToken));
        return new TokenPair(accessToken, refreshToken);
    }

    public MeResponse getMyInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return new MeResponse(user.getUserId(), user.getUsername(), user.getRole());
    }
}
