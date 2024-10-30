package com.nubble.backend.userold.session.service;

import com.nubble.backend.userold.session.domain.Session;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    boolean existsByAccessId(String sessionId);

    Optional<Session> findByAccessId(String newSessionId);
}
