package com.nubble.backend.comment.domain;

import com.nubble.backend.post.domain.Post;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
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
    public GuestComment(String content, LocalDateTime createdAt, Post post, String guestName,
            String guestPassword) {
        super(content, createdAt, post);
        this.guestName = guestName;
        this.guestPassword = guestPassword;
    }
}
