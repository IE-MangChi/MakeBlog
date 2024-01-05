package com.blog.api.service;

import com.blog.api.domain.Session;
import com.blog.api.domain.Users;
import com.blog.api.exception.WrongSignIn;
import com.blog.api.exception.WrongSignup;
import com.blog.api.repository.SessionMapper;
import com.blog.api.repository.SessionRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Login;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public String signIn(Login login) {
        Users users = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(WrongSignIn::new);
        // 성공 로직
        Session newSession = Session.builder()
                .users(users)
                .build();
        sessionRepository.saveSession(newSession);
        return newSession.getAccessToken();
    }

    @Transactional
    public Long signInJwt(Login login) {
        Users users = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(WrongSignIn::new);
        return users.getId();
    }

    @Transactional
    public void signup(Users users) {
        Optional<Users> findUser = userRepository.findByEmail(users.getEmail());
        if (findUser.isPresent()) {
            throw new WrongSignup();
        }

        userRepository.save(users);
    }

}
