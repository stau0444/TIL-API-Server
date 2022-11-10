package com.app.thingsilove.web.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final static String DEFAULT_TARGET_URL = "/api/user/login";

    @Override
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        super.setDefaultTargetUrl(DEFAULT_TARGET_URL);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return DEFAULT_TARGET_URL;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

    }

    @Override
    public void setRequestCache(RequestCache requestCache) {
        super.setRequestCache(requestCache);
    }

}
