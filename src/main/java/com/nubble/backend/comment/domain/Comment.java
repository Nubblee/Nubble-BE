package com.nubble.backend.comment.domain;

import com.nubble.backend.common.BaseEntity;
import com.nubble.backend.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "comment_type")
@Getter
public abstract class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected Comment(String content, Post post) {
        validateContent(content);
        validatePost(post);

        this.content = content;
        this.post = post;

        post.writeComment();
    }

    private static void validateContent(String content) {
        Assert.hasText(content, "댓글 내용이 필수입니다.");
        Assert.isTrue(content.length() <= 1000, "댓글 내용은 최대 1000자까지 가능합니다.");
    }

    private static void validatePost(Post post) {
        Assert.notNull(post, "게시글을 참조해주세요.");
    }

    abstract void validateAuthority(String authorizationCode);
}
