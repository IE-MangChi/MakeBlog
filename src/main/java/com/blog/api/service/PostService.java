package com.blog.api.service;

import com.blog.api.domain.Page;
import com.blog.api.request.PostCreate;
import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostSearch;
import java.util.List;
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

    public List<Post> findAll(PostSearch postSearch) {
        int count = postRepository.count();
        Page page = new Page(postSearch.getPage(), count, postSearch.getSize());
        return postRepository.findAll(page.getOffset(), postSearch.getSize());
    }

}
