package com.example.board.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.board.domain.entity.Post;
import com.example.board.domain.repository.PostRepository;
import com.example.board.domain.vo.AuthorName;
import com.example.board.web.dto.PostResponse;
import com.example.board.web.dto.UpdatePostRequest;

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

    @Test
    void 작성자만_게시글_수정가능() {
        // given
        Post post = new Post(new AuthorName("user1"), "제목", "내용");
        postRepository.save(post);

        UpdatePostRequest request = new UpdatePostRequest("새 제목", "새 내용");
        PostResponse result = postService.updatePost(post.getPostId(), "홍길동", request);

        assertThat(result.getTitle()).isEqualTo("새 제목");
        assertThat(result.getContent()).isEqualTo("새 내용");
    }
}
