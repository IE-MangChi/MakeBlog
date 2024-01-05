package com.blog.api.repository;

import com.blog.api.domain.Users;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    void saveUser(Users users);
    Optional<Users> findByEmailAndPassword(@Param("email") String email,
                                           @Param("password") String password);
    Optional<Users> findByEmail(String email);
    //테스트 데이터 초기화용
    void deleteAll();
}
