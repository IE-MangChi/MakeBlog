package com.blog.api.repository;

import com.blog.api.domain.Post;
import com.blog.api.domain.Session;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionMapper {

    void saveSession(Session session);
    List<Session> getSessions(@Param("userId") Long userId);
    Optional<Session> findByAccessToken(String accessToken);
}
