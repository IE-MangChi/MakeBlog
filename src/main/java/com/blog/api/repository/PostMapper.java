package com.blog.api.repository;

import com.blog.api.domain.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    void save(Post post);
}
