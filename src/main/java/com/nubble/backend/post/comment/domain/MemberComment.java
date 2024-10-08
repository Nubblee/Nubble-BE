package com.nubble.backend.post.comment.domain;


import com.nubble.backend.user.domain.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;

public class MemberComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public MemberComment(String content, LocalDateTime createdAt, User user) {
        super(content, createdAt);
        this.user = user;
    }
}
