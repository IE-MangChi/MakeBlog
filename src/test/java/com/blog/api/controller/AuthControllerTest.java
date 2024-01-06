package com.blog.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.blog.api.domain.Users;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Signup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void successSignup() throws Exception {
        Signup signup = Signup.builder()
                .name("테스트")
                .email("test@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(signup);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 존재하는 이메일")
    void failSignup() throws Exception {
        Users user = Users.builder()
                .name("테스트")
                .email("test@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .name("테스트")
                .email("test@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(signup);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

}