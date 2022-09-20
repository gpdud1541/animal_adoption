package com.animal.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(LoginFailHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        logger.info("***************************************************************************************************");
        logger.info("LoginFailHandler:onAuthenticationFailure:session = " + session);
        logger.info("LoginFailHandler:onAuthenticationFailure:session id = " + sessionId);
        logger.info("***************************************************************************************************");

        request.setAttribute("loginResult","fail");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
    }
}
