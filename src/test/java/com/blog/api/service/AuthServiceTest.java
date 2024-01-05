package com.blog.api.service;

import com.blog.api.domain.Users;
import com.blog.api.exception.WrongSignIn;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Login;
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
    @DisplayName("로그인 성공 - loginJwt")
    void successSignInJwt() {
        Users user = Users.builder()
                .name("테스트")
                .email("hsm9832@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("hsm9832@naver.com")
                .password("1234")
                .build();
        authService.signInJwt(login);

        Users findUser = userRepository.findByEmail(login.getEmail()).get();
        Assertions.assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
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

    @Test
    @DisplayName("회원가입 성공 후 로그인 실패")
    void successSignupAndLoginFail() {
        Users user = Users.builder()
                .name("테스트")
                .email("test@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("test@naver.com")
                .password("4321")
                .build();

        Assertions.assertThatThrownBy(() -> authService.signInJwt(login))
                        .isInstanceOf(WrongSignIn.class);
    }
}