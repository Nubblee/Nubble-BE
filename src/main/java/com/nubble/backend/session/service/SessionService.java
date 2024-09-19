package com.nubble.backend.session.service;

import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.respository.SessionRepository;
import com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.session.service.SessionInfo.SessionCreateInfo;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;
    private final AbstractSessionIdGenerator sessionIdGenerator;
    private final SessionRepository sessionRepository;

    @Transactional
    public SessionCreateInfo createSession(SessionCreateCommand command) {
        User user = userRepository.findByUsernameAndPassword(command.username(), command.password())
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾지 못했습니다."));

        Session newSession = Session.builder()
                .accessId(sessionIdGenerator.generateUniqueSessionId())
                .user(user)
                .build();
        sessionRepository.save(newSession);

        // todo: 세션Entity를 쿠키 외부 속성값들 + 생성된 세선ID로 만든다.
        return SessionCreateInfo.builder()
                .userId(user.getId())
                .sessionId(newSession.getAccessId())
                .build();
    }
}
