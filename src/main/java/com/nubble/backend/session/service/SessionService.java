package com.nubble.backend.session.service;

import com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.session.service.SessionInfo.SessionCreateInfo;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;

    public SessionCreateInfo createSession(SessionCreateCommand command) {
        User user = userRepository.findByUsernameAndPassword(command.username(), command.password())
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾지 못했습니다."));

        // todo: 세션ID를 생성한다.(세션ID는 중복되면 안된다, 중복될 경우 재생성한다)
        // todo: 세션Entity를 쿠키 외부 속성값들 + 생성된 세선ID로 만든다.
        // todo: 세션Entity를 데이터베이스에 저장하고 값들을 반환한다.

        return SessionCreateInfo.builder()
                .userId(user.getId())
                .build();
    }
}
