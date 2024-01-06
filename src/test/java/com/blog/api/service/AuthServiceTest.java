package com.blog.api.service;

import com.blog.api.domain.Users;
import com.blog.api.exception.WrongSignIn;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Signup;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
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
        Signup signup = Signup.builder()
                .name("테스트")
                .email("test@naver.com")
                .password("1234")
                .build();
        authService.signup(signup);

        Optional<Users> findUser = userRepository.findByEmail(signup.getEmail());
        Assertions.assertThat(findUser).isNotEmpty();
    }
}