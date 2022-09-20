package com.animal.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
        implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogoutSuccessHandler.class);

    public LogoutSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        String sessionId = session.getId();
//        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
//        MemberAuth memberAuth = securityUser.getMemberAuth();

        logger.info("***************************************************************************************************");
        logger.info("LogoutSuccessHandler:onLogoutSuccess:session = " + session);
        logger.info("***************************************************************************************************");
        logger.info("LogoutSuccessHandler:onLogoutSuccess:session id = " + sessionId);
        logger.info("***************************************************************************************************");

        super.onLogoutSuccess(request, response, authentication);
    }
}
