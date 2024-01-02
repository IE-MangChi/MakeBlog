package com.blog.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.blog.api.domain.Session;
import com.blog.api.domain.Users;
import com.blog.api.repository.SessionRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
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
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SessionRepository sessionRepository;

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

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 후 세션 생성")
    void successLoginAndCreateSession() throws Exception {
        Users users = Users.builder()
                .name("테스트")
                .email("test9832@naver.com")
                .password("1234")
                .build();
        userRepository.save(users);

        List<Session> beforeSessions = sessionRepository.getSessions(users.getId());
        int beforeSessionCount = beforeSessions.size();

        Login login = Login.builder()
                .email("test9832@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        List<Session> afterSessions = sessionRepository.getSessions(users.getId());
        int afterSessionCount = afterSessions.size();
        // Users에 Session갯수와 Session에 user_id인거 갯수가 동일하다로 테스트해야하나? 근데 테스트 복잡해지면 오염묻을거같은데
        Assertions.assertThat(afterSessionCount).isEqualTo(beforeSessionCount + 1);
    }

}