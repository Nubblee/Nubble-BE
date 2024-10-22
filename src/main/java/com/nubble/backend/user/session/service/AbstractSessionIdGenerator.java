package com.nubble.backend.user.session.service;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractSessionIdGenerator {

    protected final SessionRepository sessionRepository;
    protected final int maxAttempts;

    public final String generateUniqueSessionId() {
        return attemptToGenerateUniqueId(0)
                .orElseThrow(() -> new RuntimeException("유니크한 세션 ID 생성을 실패하였습니다."));
    }

    /**
     * 재귀를 통해 유니크한 세션ID를 획득할 때까지 시도합니다. 시도 횟수가 maxAttempts가 되면 Optional.empty()를 반환합니다.
     * @param attempts 현재 시도 횟수
     * @return 유니크한 세션ID, 실패시 Optional.empty()
     */
    private Optional<String> attemptToGenerateUniqueId(int attempts) {
        if (attempts >= maxAttempts) {
            return Optional.empty();
        }

        String sessionId = generateSessionId();
        if (!sessionRepository.existsByAccessId(sessionId)) {
            return Optional.of(sessionId);
        }
        return attemptToGenerateUniqueId(attempts + 1);
    }

    protected abstract String generateSessionId();
}
