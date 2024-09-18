package com.nubble.backend.session.service;

import com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.session.service.SessionInfo.SessionCreateInfo;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public SessionCreateInfo createSession(SessionCreateCommand command) {
        // todo: userName, password와 매핑되는 유저를 찾는다. 없을 경우 예외 발생
        // todo: 세션ID를 생성한다.(세션ID는 중복되면 안된다, 중복될 경우 재생성한다)
        // todo: 세션Entity를 쿠키 외부 속성값들 + 생성된 세선ID로 만든다.
        // todo: 세션Entity를 데이터베이스에 저장하고 값들을 반환한다.
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }
}
