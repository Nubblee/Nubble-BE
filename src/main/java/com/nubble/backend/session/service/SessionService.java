package com.nubble.backend.session.service;

import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
    public SessionInfo createSession(SessionCreateCommand command) {
        User user = userRepository.findByUsernameAndPassword(command.username(), command.password())
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾지 못했습니다."));

        Session newSession = Session.builder()
                .accessId(sessionIdGenerator.generateUniqueSessionId())
                .user(user)
                .expireAt(LocalDateTime.now().plusMonths(1)) // todo 설정 필드를 통해 뽑아야 함
                .build();
        sessionRepository.save(newSession);

        return SessionInfo.fromDomain(newSession);
    }

    // todo validator 빈을 만들어 분리
    @Transactional(readOnly = true)
    public void validateSession(String sessionAccessId) {
        if (sessionAccessId == null) {
            throw new RuntimeException("세션이 존재하지 않습니다.");
        }
        if (sessionRepository.findByAccessId(sessionAccessId).isEmpty()) {
            throw new RuntimeException("유효하지 않은 세션입니다.");
        }
    }
}
