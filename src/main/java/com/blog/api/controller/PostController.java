package com.blog.api.controller;

import com.blog.api.config.data.UserSession;
import com.blog.api.domain.Post;
import com.blog.api.request.PostCreate;
import com.blog.api.request.PostEdit;
import com.blog.api.request.PostSearch;
import com.blog.api.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/foo")
    public Long foo(UserSession userSession) {
        return userSession.getId();
    }

    @PostMapping("/posts")
    public Long post(@RequestBody @Valid PostCreate postCreate) {
        return postService.write(postCreate);
    }

    @PostMapping("/posts")
    public void postAuth(@RequestBody @Valid PostCreate postCreate, UserSession userSession) {
//        return postService.write(postCreate, userSession.getId());
    }

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable("postId") Long postId) {
        return postService.findById(postId);
    }

    @GetMapping("/posts")
    public List<Post> posts(@ModelAttribute("postSearch") PostSearch postSearch) {
        // 여기서 return값으로 Post보다는 PostResponse 클래스 하나 만들어서 보내는게 더 좋을듯?
        return postService.findAll(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable("postId") Long postId, @RequestBody @Valid PostEdit postEdit) {
        postService.edit(postId, postEdit);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable("postId") Long postId) {
        postService.delete(postId);
    }
}
