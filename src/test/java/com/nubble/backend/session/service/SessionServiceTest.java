package com.nubble.backend.session.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.session.service.SessionInfo.SessionCreateInfo;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private AbstractSessionIdGenerator sessionIdGenerator;
    @Autowired
    private SessionRepository sessionRepository;

    @DisplayName("username, password와 매핑되는 User가 있다면, 새로운 세션을 발급합니다.")
    @Test
    void createSession_success() {
        // given
        User user = User.builder()
                .username("user")
                .password("1234")
                .build();
        userRepository.save(user);

        SessionCommand.SessionCreateCommand command = SessionCreateCommand.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        String newSessionId = UUID.randomUUID().toString();
        given(sessionIdGenerator.generateUniqueSessionId())
                .willReturn(newSessionId);

        // when
        SessionCreateInfo actual = sessionService.createSession(command);

        // then
        Assertions.assertAll(
                () -> assertThat(actual.userId()).isEqualTo(user.getId()),
                () -> assertThat(actual.sessionId()).isEqualTo(newSessionId),
                () -> {
                    Optional<Session> byAccessId = sessionRepository.findByAccessId(newSessionId);
                    assertThat(byAccessId).isPresent();
                    assertThat(byAccessId.get().getAccessId()).isEqualTo(newSessionId);
                });
    }
}
