package com.app.thingsilove.core.user;

public class NotExistUserException extends RuntimeException{
    private final static String  ERROR_MESSAGE = "존재하지 않는 유저입니다.";
    public NotExistUserException() {
        super(ERROR_MESSAGE);
    }
}
