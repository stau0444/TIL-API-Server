package com.app.thingsilove.web.user;

import com.app.thingsilove.web.common.security.InvalidSessionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HomeController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(HttpServletResponse response){
        System.out.println("aws health check");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        return ResponseEntity.status(200).headers(headers).body("");
    }
    @GetMapping("/")
    public String sessionExpired(){
        throw new InvalidSessionException();
    }

    @GetMapping("/til/home")
    public String tilHome(){
        return "til/home";
    }
}
