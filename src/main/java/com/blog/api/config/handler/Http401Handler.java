package com.blog.api.config.handler;

import com.blog.api.resonse.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
public class Http401Handler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("401")
                .message("로그인이 필요합니다.")
                .build();
        String json = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(Integer.parseInt(errorResponse.getCode()));
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);

    }
}
