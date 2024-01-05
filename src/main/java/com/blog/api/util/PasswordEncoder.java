package com.blog.api.util;

public interface PasswordEncoder {

    String getEncodePassword(String rawPassword);
    Boolean isMatches(String rawPassword, String encodePassword);
}
