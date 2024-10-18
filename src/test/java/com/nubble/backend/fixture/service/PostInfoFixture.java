package com.nubble.backend.fixture.service;

import com.nubble.backend.fixture.PostFixture;
import com.nubble.backend.post.service.PostInfo;
import com.nubble.backend.post.shared.PostStatusDto;

public class PostInfoFixture {

    private PostInfoFixture() {
    }

    public static PostDtoFixture aPostDto() {
        return new PostDtoFixture();
    }

    public static class PostDtoFixture {

        private final PostInfo.PostDto.PostDtoBuilder builder;

        private PostDtoFixture() {
            builder = PostInfo.PostDto.builder()
                    .title(PostFixture.DEFAULT_TITLE)
                    .content(PostFixture.DEFAULT_CONTENT)
                    .thumbnailUrl(PostFixture.DEFAULT_THUMBNAIL_URL)
                    .description(PostFixture.DEFAULT_DESCRIPTION)
                    .status(PostStatusDto.valueOf(PostFixture.DEFAULT_POST_STATUS.name()));
        }

        public PostInfo.PostDto build() {
            return builder.build();
        }

        public PostDtoFixture withId(long id) {
            builder.id(id);
            return this;
        }

        public PostDtoFixture withUserId(long userId) {
            builder.userId(userId);
            return this;
        }

        public PostDtoFixture withBoardId(long boardId) {
            builder.boardId(boardId);
            return this;
        }
    }
}
