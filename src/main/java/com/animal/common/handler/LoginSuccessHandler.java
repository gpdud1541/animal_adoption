package com.animal.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    // 세션 유지 시간 ( 단위 : 분 )
    public final int SESSIONMAXINACTIVEINTERVAL_MIN = 60;

    public LoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String sessionId = session.getId();
//        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
//        MemberAuth memberAuth = securityUser.getMemberAuth();

        // 로그인 성공 계정 세션 유지 시간 ( 단위 : 초 )
        request.getSession().setMaxInactiveInterval(SESSIONMAXINACTIVEINTERVAL_MIN * 60 * 24); // 1일

        logger.info("***************************************************************************************************");
        logger.info("LoginSuccessHandler:onAuthenticationSuccess:session = " + session);
        logger.info("LoginSuccessHandler:onAuthenticationSuccess:session id = " + sessionId);
        logger.info("***************************************************************************************************");

        redirectStrategy.sendRedirect(request, response, "/");

//        resultRedirectStrategy(request, response, authentication);
//        super.onAuthenticationSuccess(request, response, authentication);
    }
}
