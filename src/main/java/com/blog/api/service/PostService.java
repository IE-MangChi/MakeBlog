package com.blog.api.service;

import com.blog.api.domain.Page;
import com.blog.api.request.PostCreate;
import com.blog.api.domain.Post;
import com.blog.api.repository.PostRepository;
import com.blog.api.request.PostEdit;
import com.blog.api.request.PostSearch;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long write(PostCreate postCreate) {
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        return postRepository.save(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
    }

    public List<Post> findAll(PostSearch postSearch) {
        int count = postRepository.count();
        Page page = new Page(postSearch.getPage(), count, postSearch.getSize());
        return postRepository.findAll(page.getOffset(), postSearch.getSize());
    }

    @Transactional
    public void edit(Long postId, PostEdit postEdit) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        postRepository.edit(post.getId(), postEdit);
    }

}
