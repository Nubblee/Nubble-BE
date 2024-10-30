package com.nubble.backend.config.resolver;

import com.nubble.backend.userold.session.domain.Session;
import com.nubble.backend.userold.session.service.SessionRepository;
import java.util.Optional;
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
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String sessionId = webRequest.getHeader("SESSION-ID"); // todo "SESSION-ID" 전역 관리
        if (sessionId == null || sessionId.isEmpty()) {
            return null;
        }

        Optional<Session> optionalSession = sessionRepository.findByAccessId(sessionId);
        if (optionalSession.isEmpty()) {
            return null;
        }

        return UserSession.builder()
                .userId(optionalSession.get().getUser().getId())
                .sessionId(optionalSession.get().getAccessId())
                .build();
    }
}
