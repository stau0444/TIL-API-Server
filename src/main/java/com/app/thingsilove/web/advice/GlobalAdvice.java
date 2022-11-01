package com.app.thingsilove.web.advice;

import com.app.thingsilove.core.user.LoginFailException;
import com.app.thingsilove.core.user.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity userExistException(UserExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = LoginFailException.class)
    public ResponseEntity loginFailException(LoginFailException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
