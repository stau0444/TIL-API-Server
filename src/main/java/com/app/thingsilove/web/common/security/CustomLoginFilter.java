package com.app.thingsilove.web.common.security;

import com.app.thingsilove.core.user.LoginFailException;
import com.app.thingsilove.core.user.User;
import com.app.thingsilove.core.user.UserRepository;
import com.app.thingsilove.web.user.LoginReq;
import com.app.thingsilove.web.user.LoginResp;
import com.app.thingsilove.web.user.category.CategoryDto;
import com.app.thingsilove.web.user.thing.ThingDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private boolean postOnly = true;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;


    public CustomLoginFilter(
                             AuthenticationManager authenticationManager,
                             ObjectMapper objectMapper,
                             PasswordEncoder encoder,
                             UserRepository userRepository
    ) {
        this.setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
        this.encoder=encoder;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/user/login");
        setAllowSessionCreation(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        LoginReq loginReq;

        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            loginReq = objectMapper.readValue(request.getInputStream(),LoginReq.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginReq.getEmail(),
                loginReq.getPwd());
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(successHandler);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        setAuthenticationSuccessHandler(new CustomAuthSuccessHandler());
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("auth fail");
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
