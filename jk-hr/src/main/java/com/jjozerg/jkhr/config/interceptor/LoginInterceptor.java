package com.jjozerg.jkhr.config.interceptor;

import com.jjozerg.jkhr.common.CroquiscomHrConstants;
import com.jjozerg.jkhr.common.MessageUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * packageName : com.jjozerg.jkhr.config
 * fileName : LoginInterceptor
 * author : joguk
 * date : 2022/02/11
 * description : login interceptor
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // 로그인 session이 없는 경우 AuthenticationException
        if (session == null || session.getAttribute(CroquiscomHrConstants.LOGIN_MEMBER) == null) {
            throw new AuthenticationException(MessageUtils.getMessages("message.session.error"));
        }

        return true;
    }
}
