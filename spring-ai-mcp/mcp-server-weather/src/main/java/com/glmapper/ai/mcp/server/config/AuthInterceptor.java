package com.glmapper.ai.mcp.server.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求API:{}",request.getRequestURI());
        String token = request.getHeader("Authorization");
        if (!isValidToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private boolean isValidToken(String token) {
        // 实现你的鉴权逻辑
        return "test-simm".equals(token);
    }
}