package com.app.thingsilove.core.user.thing;

public class NotExistThingException extends RuntimeException{
    private final static String  ERROR_MESSAGE = "존재하지 않는 컨텐츠 입니다.";
    public NotExistThingException() {
        super(ERROR_MESSAGE);
    }
}
