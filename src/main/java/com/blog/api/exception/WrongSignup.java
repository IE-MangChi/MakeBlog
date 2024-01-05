package com.blog.api.exception;

public class WrongSignup extends BlogException {

    private static final String MESSAGE = "이미 존재하는 이메일 입니다.";

    public WrongSignup() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
