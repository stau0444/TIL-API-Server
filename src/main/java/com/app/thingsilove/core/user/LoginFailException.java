package com.app.thingsilove.core.user;

public class LoginFailException extends RuntimeException {
    private final static String  ERROR_MESSAGE = "아이디 혹은 비밀번호가 잘못 되었습니다.";
    public LoginFailException() {
        super(ERROR_MESSAGE);
    }
}
