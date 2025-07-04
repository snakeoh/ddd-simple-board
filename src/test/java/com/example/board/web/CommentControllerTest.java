package com.example.board.web;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.web.dto.CreateCommentRequest;
import com.example.board.web.dto.CreatePostRequest;
import com.example.board.web.dto.UpdateCommentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @org.mockito.Mock
    private com.example.board.service.CommentService commentService;

    // @org.junit.jupiter.api.BeforeEach
    // void setUp() {
    // org.mockito.MockitoAnnotations.openMocks(this);
    // }

    // 게시글을 먼저 하나 만들고, 그에 댓글을 달기 위한 헬퍼 메서드
    private UUID createPostAndGetId() throws Exception {
        CreatePostRequest postRequest = new CreatePostRequest("작성자", "제목", "내용");

        String response = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("postId").traverse().readValueAs(UUID.class);
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    void createComment() throws Exception {
        UUID postId = createPostAndGetId();

        CreateCommentRequest request = new CreateCommentRequest("댓글 작성자", "댓글 내용");

        mockMvc.perform(post("/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName").value("댓글 작성자"))
                .andExpect(jsonPath("$.content").value("댓글 내용"));
    }

    @Test
    @DisplayName("댓글 단건 조회 테스트")
    void getCommentById() throws Exception {
        UUID postId = createPostAndGetId();
        CreateCommentRequest request = new CreateCommentRequest("조회자", "조회용 댓글");

        String response = mockMvc.perform(post("/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID commentId = objectMapper.readTree(response).get("commentId").traverse().readValueAs(UUID.class);

        mockMvc.perform(get("/comments/{commentId}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName").value("조회자"))
                .andExpect(jsonPath("$.content").value("조회용 댓글"));
    }

    @Test
    @DisplayName("게시글에 달린 댓글 목록 조회 테스트")
    void getCommentsByPostId() throws Exception {
        UUID postId = createPostAndGetId();

        CreateCommentRequest request1 = new CreateCommentRequest("작성자1", "댓글 내용 1");
        CreateCommentRequest request2 = new CreateCommentRequest("작성자2", "댓글 내용 2");

        mockMvc.perform(post("/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/posts/{postId}/comments", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteComment() throws Exception {
        UUID postId = createPostAndGetId();
        CreateCommentRequest request = new CreateCommentRequest("삭제자", "삭제용 댓글");

        String response = mockMvc.perform(post("/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID commentId = objectMapper.readTree(response).get("commentId").traverse().readValueAs(UUID.class);

        mockMvc.perform(delete("/comments/{commentId}", commentId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/comments/{commentId}", commentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateComment() throws Exception {
        // given: 게시글 생성
        UUID postId = createPostAndGetId();

        // and: 댓글 생성
        CreateCommentRequest createRequest = new CreateCommentRequest("수정자", "수정 전 댓글");

        String response = mockMvc.perform(post("/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID commentId = objectMapper.readTree(response).get("commentId").traverse().readValueAs(UUID.class);

        // when: 댓글 수정 요청
        UpdateCommentRequest updatedRequest = new UpdateCommentRequest("수정된 댓글 내용");

        mockMvc.perform(put("/comments/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName").value("수정자"))
                .andExpect(jsonPath("$.content").value("수정된 댓글 내용"));
    }

    @Test
    @DisplayName("존재하지 않는 댓글 ID로 수정 요청 시 404 응답")
    void updateCommentNotFound() throws Exception {
        // given: 존재하지 않는 댓글 ID
        UUID nonExistentCommentId = UUID.randomUUID();
        UpdateCommentRequest request = new UpdateCommentRequest("수정된 댓글 내용");

        mockMvc.perform(put("/comments/{commentId}", nonExistentCommentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("댓글이 존재하지 않습니다. ID: " + nonExistentCommentId));
    }

    @Test
    @DisplayName("내용이 비어있는 댓글 생성 시 400 응답")
    void createCommentForNonExistentPost() throws Exception {
    }

    @Test
    @DisplayName("commentId 형식이 잘못이면 400 응답")
    void createCommentForWrongCommentId() throws Exception {
    }

    @Test
    @DisplayName("댓글 수정 시 content가 비어 있으면 400 Bad Request 응답")
    void updateComment_withBlankContent_shouldReturnBadRequest() throws Exception {
        // given
        UUID commentId = UUID.randomUUID(); // 존재 여부는 중요하지 않음(validation 먼저 수행됨)
        UpdateCommentRequest request = new UpdateCommentRequest(" ");

        // when
        mockMvc.perform(post("/comments/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("content: 댓글 내용은 비어 있을 수 없습니다."));
    }

    @Test
    void 댓글_삭제_성공() throws Exception {
        UUID commentId = UUID.randomUUID();

        doNothing().when(commentService).delete(commentId);

        mockMvc.perform(delete("/comments/{commentId}", commentId))
                .andExpect(status().isNoContent());
    }
}
