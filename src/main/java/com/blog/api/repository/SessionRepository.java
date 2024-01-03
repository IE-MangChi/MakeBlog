package com.blog.api.repository;

import com.blog.api.domain.Session;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final SessionMapper sessionMapper;

    public void saveSession(Session session) {
        sessionMapper.saveSession(session);
    }

    public List<Session> getSessions(Long userId) {
        return sessionMapper.getSessions(userId);
    }
    public Optional<Session> findByAccessToken(String accessToken) {
        return sessionMapper.findByAccessToken(accessToken);
    }
}
