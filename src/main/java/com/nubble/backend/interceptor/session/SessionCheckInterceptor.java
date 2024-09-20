package com.nubble.backend.interceptor.session;

import com.nubble.backend.session.controller.SessionCookieProperties;
import com.nubble.backend.session.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class SessionCheckInterceptor implements HandlerInterceptor {

    private final SessionCookieProperties sessionCookieProperties;
    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            SessionRequired sessionRequired = handlerMethod.getMethodAnnotation(SessionRequired.class);
            if (sessionRequired != null) {
                String sessionId = getSessionIdFromCookie(request);
                sessionService.validateSession(sessionId);
            }
        }
        return true;
    }

    private String getSessionIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (sessionCookieProperties.getName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
