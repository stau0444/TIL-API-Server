package com.app.thingsilove.core.user.category;

public class NotExistCategoryException extends RuntimeException{
    private final static String  ERROR_MESSAGE = "존재하지 않는 카테고리 입니다.";
    public NotExistCategoryException() {
        super(ERROR_MESSAGE);
    }
}
