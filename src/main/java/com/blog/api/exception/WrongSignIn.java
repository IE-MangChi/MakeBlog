package com.blog.api.exception;

public class WrongSignIn extends BlogException{

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public WrongSignIn() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
