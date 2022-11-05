package com.app.thingsilove.web.common.security;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomOncePerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"https://things-i-love.netlify.app");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,"*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName();
        boolean hasAlreadyFilteredAttribute = request.getAttribute(alreadyFilteredAttributeName) != null;

        if (skipDispatch(httpRequest) || shouldNotFilter(httpRequest)) {
            // Proceed without invoking this filter...
            filterChain.doFilter(request, response);
        }
        else if (hasAlreadyFilteredAttribute) {
            if (DispatcherType.ERROR.equals(request.getDispatcherType())) {
                doFilterNestedErrorDispatch(httpRequest, httpResponse, filterChain);
                return;
            }

            // Proceed without invoking this filter...
            filterChain.doFilter(request, response);
        }
        else {
            // Do invoke this filter...
            request.setAttribute(alreadyFilteredAttributeName, Boolean.TRUE);
            try {
                doFilterInternal(httpRequest, httpResponse, filterChain);
            }
            finally {
                // Remove the "already filtered" request attribute for this request.
                request.removeAttribute(alreadyFilteredAttributeName);
            }
        }
    }
    private boolean skipDispatch(HttpServletRequest request) {
        if (isAsyncDispatch(request) && shouldNotFilterAsyncDispatch()) {
            return true;
        }
        if (request.getAttribute(WebUtils.ERROR_REQUEST_URI_ATTRIBUTE) != null && shouldNotFilterErrorDispatch()) {
            return true;
        }
        return false;
    }
}
