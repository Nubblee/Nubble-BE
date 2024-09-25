package com.nubble.backend.config.resolver;

import com.nubble.backend.interceptor.session.SessionRequired;
import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class)
                && parameter.getMethod().isAnnotationPresent(SessionRequired.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String sessionId = webRequest.getHeader("SESSION-ID"); // todo "SESSION-ID" 전역 관리
        if (sessionId == null || sessionId.isEmpty()) {
            throw new RuntimeException("세션 아이디가 누락되었습니다.");
        }

        Session foundSession = sessionRepository.findByAccessId(sessionId)
                .orElseThrow(() -> new RuntimeException("올바르지 않은 세션ID입니다."));

        return UserSession.builder()
                .userId(foundSession.getUser().getId())
                .sessionId(foundSession.getAccessId())
                .build();
    }
}
