package com.blog.api.service;

import com.blog.api.domain.Session;
import com.blog.api.domain.Users;
import com.blog.api.exception.WrongSignIn;
import com.blog.api.exception.WrongSignup;
import com.blog.api.repository.SessionRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Login;
import com.blog.api.request.Signup;
import com.blog.api.util.PasswordEncoder;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public String signIn(Login login) {
        Users users = userRepository.findByEmail(login.getEmail())
                .orElseThrow(WrongSignIn::new);
        // 성공 로직
        Boolean matches = passwordEncoder.isMatches(login.getPassword(), users.getPassword());

        if (!matches) {
            throw new WrongSignIn();
        }

        Session newSession = Session.builder()
                .users(users)
                .build();
        sessionRepository.saveSession(newSession);
        return newSession.getAccessToken();
    }

    @Transactional
    public Long signInJwt(Login login) {
        Users users = userRepository.findByEmail(login.getEmail())
                .orElseThrow(WrongSignIn::new);

        Boolean matches = passwordEncoder.isMatches(login.getPassword(), users.getPassword());

        if (!matches) {
            throw new WrongSignIn();
        }

        return users.getId();
    }

    @Transactional
    public void signup(Signup signup) {
        Optional<Users> findUser = userRepository.findByEmail(signup.getEmail());
        if (findUser.isPresent()) {
            throw new WrongSignup();
        }

        String encodePassword = passwordEncoder.getEncodePassword(signup.getPassword());

        Users user = Users.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encodePassword)
                .build();

        userRepository.save(user);
    }

}
