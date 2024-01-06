package com.blog.api.service;

import com.blog.api.domain.Users;
import com.blog.api.exception.WrongSignup;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Signup;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signup(Signup signup) {
        Optional<Users> findUser = userRepository.findByEmail(signup.getEmail());
        if (findUser.isPresent()) {
            throw new WrongSignup();
        }

        String encodePassword = passwordEncoder.encode(signup.getPassword());

        Users user = Users.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encodePassword)
                .build();

        userRepository.save(user);
    }

}
