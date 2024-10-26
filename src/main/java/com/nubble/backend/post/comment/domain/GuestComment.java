package com.nubble.backend.post.comment.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("GUEST")
@Getter
public class GuestComment extends Comment {

    private String guestName;
    private String guestPassword;

    @Builder
    public GuestComment(String content, LocalDateTime createdAt, String guestName,
            String guestPassword) {
        super(content, createdAt);
        this.guestName = guestName;
        this.guestPassword = guestPassword;
    }

    public boolean matchCredentials(String password) {
        return Objects.equals(guestPassword, password);
    }
}
