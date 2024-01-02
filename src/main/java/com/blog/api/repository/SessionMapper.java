package com.blog.api.repository;

import com.blog.api.domain.Session;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionMapper {

    void saveSession(Session session);
    List<Session> getSessions(@Param("userId") Long userId);
}
