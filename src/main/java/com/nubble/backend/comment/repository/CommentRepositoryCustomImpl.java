package com.nubble.backend.comment.repository;

import static com.nubble.backend.comment.domain.QComment.comment;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.domain.member.QMemberComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.as(QMemberComment.class).user).fetchJoin()
                .where(comment.post.id.eq(postId))
                .orderBy(comment.createdAt.desc())
                .fetch();
    }
}
