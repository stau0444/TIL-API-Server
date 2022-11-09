package com.app.thingsilove.web.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionStrategy implements InvalidSessionStrategy{

    Logger logger = LoggerFactory.getLogger(SessionStrategy.class);
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("invalid session login again");
        throw new InvalidSessionException();
    }
}
