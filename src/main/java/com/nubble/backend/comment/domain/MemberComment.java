package com.nubble.backend.comment.domain;


import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.user.domain.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("MEMBER")
@Getter
public class MemberComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public MemberComment(String content, LocalDateTime createdAt, User user) {
        super(content, createdAt);
        this.user = user;
    }

    public void validateAuthor(long authorId) {
        if (authorId != user.getId()) {
            throw new NoAuthorizationException("댓글의 작성자가 아닙니다.");
        }
    }
}
