package com.nubble.backend.user.service;

import com.nubble.backend.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String s, String password);

    default User getUserById(long userId) {
        return findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
    }
}
