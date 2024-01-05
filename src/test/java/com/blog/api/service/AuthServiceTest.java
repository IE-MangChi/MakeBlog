package com.blog.api.service;

import com.blog.api.domain.Users;
import com.blog.api.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void successSignup() {
        Users users = Users.builder()
                .name("테스트")
                .email("test@naver.com")
                .password("1234")
                .build();

        authService.signup(users);

        Users findUser = userRepository.findByEmail(users.getEmail()).get();
        Assertions.assertThat(findUser.getName()).isEqualTo(users.getName());
        Assertions.assertThat(findUser.getEmail()).isEqualTo(users.getEmail());
        Assertions.assertThat(findUser.getPassword()).isEqualTo(users.getPassword());
    }

}