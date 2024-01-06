package com.blog.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.blog.api.domain.Post;
import com.blog.api.exception.PostNotFound;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import com.blog.api.request.PostEdit;
import com.blog.api.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clear() {
        postRepository.deleteAll();
    }

    // 이런 테스트는 Get요청에 맞는듯
    @Test
    @WithMockUser(username = "hsm9832@naver.com", roles = {"ADMIN"})
    @DisplayName("/posts 요청시 title값은 필수다")
    void postEmptyTitleTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": null, \"content\": \"내용입니다.\"}")
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "hsm9832@naver.com", roles = {"ADMIN"})
    @DisplayName("/posts 요청시 DB에 값이 저장")
    void postResultOnDBTest() throws Exception {
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        // DB값 검증 로직
        String contentAsString = mvcResult.getResponse().getContentAsString();
        long postId = Long.parseLong(contentAsString);
        Post post = postService.findById(postId);
        Assertions.assertThat(post.getTitle()).isEqualTo("제목입니다.");
        Assertions.assertThat(post.getContent()).isEqualTo("내용입니다.");
    }

    @Test
    @DisplayName("글 1개 조회")
    void boardGetApiTest() throws Exception {
        PostCreate postCreate = PostCreate.builder()
                .title("제목22")
                .content("내용22")
                .build();
        Long postId = postService.write(postCreate);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("제목22"))
                .andExpect(jsonPath("$.content").value("내용22"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void boardPageOneTest() throws Exception {
        IntStream.range(1, 29)
                .forEach(i -> {
                    Post post = Post.builder()
                            .title("제목" + i)
                            .content("내용" + i)
                            .build();
                    postRepository.save(post);
                });

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "hsm9832@naver.com", roles = {"ADMIN"})
    @DisplayName("글 수정")
    void boardUpdateTest() throws Exception {
        Post post = Post.builder()
                .title("제목 수정 전")
                .content("내용 수정 전")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정 후")
                .content("내용 수정 후")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        Post postAfter = postService.findById(post.getId());
        Assertions.assertThat(postAfter.getTitle()).isEqualTo("제목 수정 후");
        Assertions.assertThat(postAfter.getContent()).isEqualTo("내용 수정 후");
    }

    @Test
    @WithMockUser(username = "hsm9832@naver.com", roles = {"ADMIN"})
    @DisplayName("글 삭제")
    void boardDeleteTest() throws Exception {
        Post post = Post.builder()
                .title("제목 수정 전")
                .content("내용 수정 전")
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        Assertions.assertThatThrownBy(() -> postService.findById(post.getId()))
                .isInstanceOf(PostNotFound.class);
    }

    @Test
    @WithMockUser(username = "hsm9832@naver.com", roles = {"ADMIN"})
    @DisplayName("존재하지 않는 글 조회")
    void boardGetApiFailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }
}