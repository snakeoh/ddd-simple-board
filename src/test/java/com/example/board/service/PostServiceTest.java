package com.example.board.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.PostRepository;
import com.example.board.domain.vo.AuthorName;

// @SpringBootTest
// @ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    // @MockBean
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    // void createPostTest() {
    void 게시글_생성_성공() {
        Post post = new Post(new AuthorName("홍길동"), "제목", "내용");
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postService.createPost(new AuthorName("홍길동"), "제목", "내용");

        assertThat(result.getAuthorName().getValue()).isEqualTo("홍길동");
        assertThat(result.getTitle()).isEqualTo("제목");
    }
}
