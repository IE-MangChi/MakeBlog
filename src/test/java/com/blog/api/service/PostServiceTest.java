package com.blog.api.service;

import com.blog.api.domain.Post;
import com.blog.api.exception.PostNotFound;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostCreate;
import com.blog.api.request.PostEdit;
import com.blog.api.request.PostSearch;
import java.util.List;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @AfterEach
    void clear() {
        postRepository.deleteAll();
    }

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
    @DisplayName("글 1페이지 조회")
    void boardPageOneTest() {
        IntStream.range(1, 30)
                .forEach(i -> {
                    Post post = Post.builder()
                            .title("제목" + i)
                            .content("내용" + i)
                            .build();
                    postRepository.save(post);
                });

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(5)
                .build();

        List<Post> posts = postService.findAll(postSearch);
        Assertions.assertThat(posts.size()).isEqualTo(5);
        Assertions.assertThat(posts.get(0).getId()).isEqualTo(30);
        Assertions.assertThat(posts.get(4).getId()).isEqualTo(26);
    }

    @Test
    @DisplayName("글 수정")
    void boardUpdateTest() {
        Post post = Post.builder()
                .title("제목 수정 전")
                .content("내용 수정 전")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목 수정 후")
                .content("내용 수정 후")
                .build();

        postService.edit(post.getId(), postEdit);
        Post postAfter = postService.findById(post.getId());
        Assertions.assertThat(postAfter.getTitle()).isEqualTo("제목 수정 후");
        Assertions.assertThat(postAfter.getContent()).isEqualTo("내용 수정 후");
    }

    @Test
    @DisplayName("글 수정")
    void boardDeleteTest() {
        Post post = Post.builder()
                .title("제목 수정 전")
                .content("내용 수정 전")
                .build();
        postRepository.save(post);

        postService.delete(post.getId());
        Assertions.assertThatThrownBy(() -> postService.findById(post.getId()))
                .isInstanceOf(PostNotFound.class);
    }
}