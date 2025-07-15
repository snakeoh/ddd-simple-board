package com.example.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.domain.entity.User;
import com.example.board.repository.UserRepository;
import com.example.board.security.JwtTokenProvider;
import com.example.board.security.Sha256PasswordEncoder;

@Service
public class AuthService {
    @Autowired
    private Sha256PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    // private final JwtUtil jwtUtil;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        String encoded = passwordEncoder.encode(rawPassword);
        if (!user.getPassword().equals(encoded)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        return jwtTokenProvider.generateToken(user.getUsername(), user.getRole().name());
    }

}
