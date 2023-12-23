package com.blog.api.service;

import com.blog.api.request.PostCreate;
import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long write(PostCreate postCreate) {
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        return postRepository.save(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId);
    }

}
