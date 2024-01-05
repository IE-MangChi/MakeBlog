package com.blog.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.blog.api.domain.Users;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Login;
import com.blog.api.request.Signup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.concurrent.TimeUnit;
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
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인 성공")
    void successLogin() throws Exception {
        Login login = Login.builder()
                .email("hsm9832@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/loginJWT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 후 토큰 생성")
    void successLoginAndCreateSession() throws Exception {
        Users users = Users.builder()
                .name("테스트")
                .email("test9832@naver.com")
                .password("1234")
                .build();
        userRepository.save(users);

        Login login = Login.builder()
                .email("test9832@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/loginJWT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());

    }

    @Test
    @DisplayName("로그인 후 권한있는 페이지 접속")
    void loginAndAuthedPage() throws Exception {
        Login login = Login.builder()
                .email("hsm9832@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth/loginJWT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        String token = JsonPath.read(responseBody, "$.accessToken");

        mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

//    @Test
//    @DisplayName("로그인 후 토큰 만료 후 권한이 있는 페이지 접속 불가")
//    void loginAndAuthedPageFail() throws Exception {
//        Login login = Login.builder()
//                .email("hsm9832@naver.com")
//                .password("1234")
//                .build();
//
//        String json = objectMapper.writeValueAsString(login);
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth/loginJWT")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        String responseBody = mvcResult.getResponse().getContentAsString();
//        String token = JsonPath.read(responseBody, "$.accessToken");
//
//        TimeUnit.SECONDS.sleep(11);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/foo")
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(print());
//    }

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
        Users users = Users.builder()
                .name("테스트")
                .email("test@naver.com")
                .password("1234")
                .build();
        userRepository.save(users);

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