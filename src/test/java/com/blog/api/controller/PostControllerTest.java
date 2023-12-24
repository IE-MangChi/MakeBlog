package com.blog.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import com.blog.api.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    // 이런 테스트는 Get요청에 맞는듯
    @Test
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
        Post post = postRepository.findById(postId);
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
}