package com.blog.api.exception;

public class UserNotFound extends BlogException{

    private final static String MESSAGE = "존재하지 유저 입니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    public UserNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
