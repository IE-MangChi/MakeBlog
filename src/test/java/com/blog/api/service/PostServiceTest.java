package com.blog.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("글 작성 테스트")
    void boardPostTest() {
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        Long postId = postService.write(postCreate);

        Post post = postService.findById(postId);
        Assertions.assertThat(post.getTitle()).isEqualTo("제목");
        Assertions.assertThat(post.getContent()).isEqualTo("내용");
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