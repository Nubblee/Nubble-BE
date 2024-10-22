package com.nubble.backend.config.interceptor.session;

import com.nubble.backend.user.session.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class SessionCheckInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            SessionRequired sessionRequired = handlerMethod.getMethodAnnotation(SessionRequired.class);
            if (sessionRequired != null) {
                String sessionId = getSessionIdFromHeader(request);
                sessionService.validateSession(sessionId);
            }
        }
        return true;
    }

    private String getSessionIdFromHeader(HttpServletRequest request) {
        return request.getHeader("SESSION-ID"); // todo 전역에서 관리할 수 있도록 변경
    }
}
