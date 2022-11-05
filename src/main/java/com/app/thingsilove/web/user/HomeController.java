package com.app.thingsilove.web.user;

import com.app.thingsilove.web.common.security.InvalidSessionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String sessionExpired(){
        throw new InvalidSessionException();
    }

    @GetMapping("/til/home")
    public String tilHome(){
        return "til/home";
    }
}
