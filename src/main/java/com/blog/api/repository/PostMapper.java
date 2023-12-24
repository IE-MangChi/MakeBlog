package com.blog.api.repository;

import com.blog.api.domain.Post;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    Long save(Post post);
    Optional<Post> findById(Long postId);
    int count();
    List<Post> findAll(@Param("offset") int offset,
                       @Param("boardSize") int boardSize);
}
