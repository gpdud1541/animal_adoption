package com.animal.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    // 권한없는 사용자 접근 시 처리 Handler
    private static final Logger logger = LoggerFactory.getLogger(WebAccessDeniedHandler.class);

//    @Autowired
//    private MemberService memberService;
//
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ade)
            throws IOException, ServletException {

//        res.setStatus(HttpStatus.FORBIDDEN.value());

        // 차후에 보완해야할 부분 !!! 현우 ( 참조 : https://blog.jiniworld.me/53 )
//
//        if(ade instanceof AccessDeniedException) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if(authentication != null) {
//                SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
//                SecurityContextHolder.clearContext();
//            }
//        }
        req.getRequestDispatcher("/").forward(req, res);
    }
}
