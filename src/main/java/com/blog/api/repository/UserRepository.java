package com.blog.api.repository;

import com.blog.api.domain.Users;
import com.blog.api.request.Login;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public Optional<Users> findByEmailAndPassword(String email, String password) {
        return userMapper.findByEmailAndPassword(email, password);
    }

}
