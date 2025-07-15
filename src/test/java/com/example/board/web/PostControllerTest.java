package com.example.board.web;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.repository.UserRepository;
import com.example.board.security.JwtTokenProvider;
import com.example.board.security.Sha256PasswordEncoder;
import com.example.board.web.dto.CreatePostRequest;
import com.example.board.domain.entity.User;
import com.example.board.domain.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Sha256PasswordEncoder sha256;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        // 사용자 생성
        userRepository.save(new User("user1", sha256.encode("pass"), Role.USER));
        userRepository.save(new User("admin", sha256.encode("pass"), Role.ADMIN));

        String userToken = jwtTokenProvider.generateToken("user1", Role.USER.name());
        String adminToken = jwtTokenProvider.generateToken("admin", Role.ADMIN.name());
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void createPostTest() throws Exception {
        CreatePostRequest request = new CreatePostRequest("작성자1", "제목1", "내용1");

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName").value("작성자1"))
                .andExpect(jsonPath("$.title").value("제목1"))
                .andExpect(jsonPath("$.content").value("내용1"));
    }

    @Test
    @DisplayName("게시글 단건 조회 테스트")
    void getPostById() throws Exception {
        CreatePostRequest request = new CreatePostRequest("작성자", "조회 제목", "조회 내용");

        String response = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID postId = objectMapper.readTree(response).get("postId").traverse().readValueAs(UUID.class);

        mockMvc.perform(get("/posts/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("조회 제목"))
                .andExpect(jsonPath("$.content").value("조회 내용"));

    }

    @Test
    @DisplayName("제목이 비어 있을 경우 게시글 작성 실패")
    void createPost_WithEmptyTitle_shouldReturnBadRequest() throws Exception {
        CreatePostRequest request = new CreatePostRequest("작성자", "", "내용");

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("title: 제목은 필수입니다."));
    }

}
