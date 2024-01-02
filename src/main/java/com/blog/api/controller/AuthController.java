package com.blog.api.controller;

import com.blog.api.domain.Users;
import com.blog.api.exception.WrongSignIn;
import com.blog.api.repository.UserRepository;
import com.blog.api.request.Login;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public Users login(@RequestBody @Valid Login login) throws Exception {
        Users user = userRepository.findByEmailAndPassword(login.getEmail(),
                login.getPassword()).orElseThrow(WrongSignIn::new);
        return user;
    }


}
