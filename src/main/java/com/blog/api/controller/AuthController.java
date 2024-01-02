package com.blog.api.controller;

import com.blog.api.request.Login;
import com.blog.api.resonse.SessionResponse;
import com.blog.api.service.AuthService;
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

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login) {
        String accessToken = authService.signIn(login);
        return new SessionResponse(accessToken);
    }
}
