package com.app.thingsilove.web.common.security;


public class InvalidSessionException extends RuntimeException {

    private final static String  ERROR_MESSAGE = "세션이 만료되었습니다. 다시 로그인 해주세요.";
    public InvalidSessionException() {
        super(ERROR_MESSAGE);
    }
}
