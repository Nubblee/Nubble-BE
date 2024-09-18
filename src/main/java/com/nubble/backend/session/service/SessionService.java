package com.nubble.backend.session.service;

import com.nubble.backend.session.service.SessionCommand.SessionCreationCommand;
import com.nubble.backend.session.service.SessionInfo.SessionCreationInfo;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public SessionCreationInfo create(SessionCreationCommand sessionCreationCommand) {
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }
}
