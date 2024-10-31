package com.nubble.backend.post.repository;

import static com.nubble.backend.post.domain.QPost.post;
import static com.nubble.backend.userold.domain.QUser.user;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
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
    public Optional<Post> findPostWithUserById(long postId) {
        Post foundPost = jpaQueryFactory.selectFrom(post)
                .from(post)
                .innerJoin(post.user, user).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(foundPost);
    }
}
