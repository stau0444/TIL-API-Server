package com.app.thingsilove.web.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    @GetMapping(
            value = "/logoutSuccess"
    )
    public ResponseEntity<String> logoutSuccess(String userId) {
        System.out.println(userId + "log out success");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("logout success");
    }
}
