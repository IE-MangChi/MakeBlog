package com.blog.api.repository;

import com.blog.api.domain.Post;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final PostMapper postMapper;

    public Long save(Post post) {
        return postMapper.save(post);
    }

    public Post findById(Long postId) {
        return postMapper.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
    }

    public int count() {
        return postMapper.count();
    }

    public List<Post> findAll(int offset, int boardSize) {
        return postMapper.findAll(offset, boardSize);
    }

}
