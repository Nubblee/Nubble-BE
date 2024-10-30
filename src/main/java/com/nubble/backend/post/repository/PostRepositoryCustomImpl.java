package com.nubble.backend.post.repository;

import static com.nubble.backend.post.domain.QPost.post;
import static com.nubble.backend.userold.domain.QUser.user;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
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
    public Post getPostWithUserById(long postId) {
        Post foundPost = jpaQueryFactory.selectFrom(post)
                .from(post)
                .innerJoin(post.user, user).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne();

        if (foundPost == null) {
            throw new EntityNotFoundException("게시글이 존재하지 않습니다.");
        }
        return foundPost;
    }
}
