package com.app.thingsilove.core.user;

public class UserExistException extends RuntimeException {

    private final static String  ERROR_MESSAGE = "이미 가입된 email 입니다.";
    public UserExistException() {
        super(ERROR_MESSAGE);
    }
}
