package com.blog.api.exception;

public class WrongSignup extends BlogException {

    private static final String MESSAGE = "로그인에 실패하였습니다.";

    public WrongSignup() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
