package com.blog.api.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class PlainPasswordEncoder implements PasswordEncoder{


    @Override
    public String getEncodePassword(String rawPassword) {
        return rawPassword;
    }

    @Override
    public Boolean isMatches(String rawPassword, String encodePassword) {
        return rawPassword.equals(encodePassword);
    }
}
