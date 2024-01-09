package com.blog.api.service;

import com.blog.api.domain.Page;
import com.blog.api.domain.Users;
import com.blog.api.exception.PostNotFound;
import com.blog.api.exception.UserNotFound;
import com.blog.api.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Long write(Long userId, PostCreate postCreate) {
        Users users = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .users(users)
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        return postRepository.save(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
    }

    public List<Post> findAll(PostSearch postSearch) {
        int count = postRepository.count();
        Page page = new Page(postSearch.getPage(), count, postSearch.getSize());
        return postRepository.findAll(page.getOffset(), postSearch.getSize());
    }

    @Transactional
    public void edit(Long postId, PostEdit postEdit) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
        postRepository.edit(post.getId(), postEdit);
    }

    public void delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
        postRepository.delete(post.getId());
    }

}
