package com.nubble.backend.comment.domain;

import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("GUEST")
@Getter
public class GuestComment extends Comment {

    @Column(length = 100)
    private String guestName;

    @Column(length = 100)
    private String guestPassword;

    @Builder
    public GuestComment(String content, Post post, String guestName, String guestPassword) {
        super(content, post);

        validateGuestName(guestName);
        validateGuestPassword(guestPassword);

        this.guestName = guestName;
        this.guestPassword = guestPassword;
    }

    private static void validateGuestPassword(String guestPassword) {
        Assert.hasText(guestPassword, "게스트 비밀번호가 비어있습니다.");
        Assert.isTrue(guestPassword.length() <= 100, "게스트 비밀번호는 100글자까지 가능합니다.");
    }

    private static void validateGuestName(String guestName) {
        Assert.hasText(guestName, "게스트 이름이 비어있습니다.");
        Assert.isTrue(guestName.length() <= 100, "게스트 이름은 100글자까지 가능합니다.");
    }


    public void validatePassword(String password) {
        if (!Objects.equals(password, this.guestPassword)) {
            throw new NoAuthorizationException("비밀번호가 일치하지 않습니다.");
        }
    }
}
