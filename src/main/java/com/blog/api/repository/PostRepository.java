package com.blog.api.repository;

import com.blog.api.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final PostMapper postMapper;

    public void save(Post post) {
        postMapper.save(post);
    }

}
