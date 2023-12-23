package com.blog.api.repository;

import com.blog.api.domain.Post;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    Long save(Post post);
    Optional<Post> findById(Long postId);
}
