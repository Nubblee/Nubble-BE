package com.nubble.backend.post.repository;

import static com.nubble.backend.post.domain.QPost.post;
import static com.nubble.backend.user.domain.QUser.user;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAllWithUserByBoardId(long boardId) {
        return jpaQueryFactory.selectFrom(post)
                .innerJoin(post.user, user).fetchJoin()
                .where(post.board.id.eq(boardId))
                .where(post.status.ne(PostStatus.DRAFT))
                .fetch();
    }

    @Override
    public Post getWithUserById(long postId) {
        return jpaQueryFactory.selectFrom(post)
                .from(post)
                .innerJoin(post.user, user).fetchJoin()
                .where(post.id.eq(postId))
                .where(post.status.ne(PostStatus.DRAFT))
                .fetchOne();
    }
}
