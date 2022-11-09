package com.app.thingsilove.web.common.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
    @GetMapping("/session-expired")
    public ResponseEntity<String> sessionExpired(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session이 만료되었습니다 다시 로그인 해주세요");
    }
    @GetMapping("/session-fail")
    public ResponseEntity<String> sessionFail(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session이 인증에 실패했습니다.");
    }
}
