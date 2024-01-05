package com.blog.api.controller;

import com.blog.api.config.JwtProvider;
import com.blog.api.config.data.UserSession;
import com.blog.api.domain.Users;
import com.blog.api.request.Login;
import com.blog.api.request.Signup;
import com.blog.api.resonse.SessionResponse;
import com.blog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Login login) {
        String accessToken = authService.signIn(login);
        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/auth/loginJWT")
    public SessionResponse loginJWT(@RequestBody @Valid Login login) {
        Long userId = authService.signInJwt(login);
        SecretKey secretKey = jwtProvider.getSecretKey();
        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(jwtProvider.getExpiration())
                .compact();
        return new SessionResponse(jws);
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody @Valid Signup signup) {
        Users user = Users.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(signup.getPassword())
                .build();
        authService.signup(user);
    }
}
