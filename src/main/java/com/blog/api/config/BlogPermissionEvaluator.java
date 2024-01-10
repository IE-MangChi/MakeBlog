package com.blog.api.config;

import com.blog.api.domain.Post;
import com.blog.api.exception.PostNotFound;
import com.blog.api.repository.PostRepository;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

@Slf4j
@RequiredArgsConstructor
public class BlogPermissionEvaluator implements PermissionEvaluator {

    private final PostRepository postRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        Post post = postRepository.findById((Long) targetId)
                .orElseThrow(PostNotFound::new);

        if (!post.getUserId().equals(principal.getUserId())) {
            log.error("[인가실패] 글에 대한 권한이 없습니다.");
            return false;
        }

        return true;
    }
}
