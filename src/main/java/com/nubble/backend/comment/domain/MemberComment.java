package com.nubble.backend.comment.domain;


import com.nubble.backend.post.domain.Post;
import com.nubble.backend.user.domain.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("MEMBER")
@Getter
public class MemberComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public MemberComment(String content,  Post post, User user) {
        super(content, post);

        validateUser(user);

        this.user = user;
    }

    private static void validateUser(User user) {
        Assert.notNull(user, "user는 null일 수 없습니다.");
    }

    @Override
    public void validateAuthority(String authorId) {
        Assert.state(user.getId() == Long.parseLong(authorId), "댓글의 작성자가 아닙니다.");
    }
}
