package com.nubble.backend.session.respository;

import com.nubble.backend.session.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    boolean existsByAccessId(String sessionId);
}
