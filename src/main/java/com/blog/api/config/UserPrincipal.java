package com.blog.api.config;

import com.blog.api.domain.Users;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(Users user) {
        super(user.getEmail(), user.getPassword(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                ));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
