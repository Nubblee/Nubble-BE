package com.nubble.backend.utils.fixture.service;

import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.service.CategoryInfo.CategoryDto;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.postold.service.PostInfo;
import com.nubble.backend.postold.service.PostInfo.PostWithUserDto;
import com.nubble.backend.utils.fixture.domain.PostFixture;
import java.time.LocalDateTime;

public class PostInfoFixture {

    private PostInfoFixture() {
    }

    public static PostDtoFixture aPostDto() {
        return new PostDtoFixture();
    }

    public static PostWithUserDtoFixture aPostWithUserDto() {
        return new PostWithUserDtoFixture();
    }

    public static PostWithCategoryDtoFixture aPostWithCategoryDto() {
        return new PostWithCategoryDtoFixture();
    }

    public static class PostDtoFixture {

        private final PostInfo.PostDto.PostDtoBuilder builder;

        private PostDtoFixture() {
            builder = PostInfo.PostDto.builder()
                    .id(162019L)
                    .userId(162020L)
                    .boardId(162021L)
                    .title(PostFixture.DEFAULT_TITLE)
                    .content(PostFixture.DEFAULT_CONTENT)
                    .thumbnailUrl(PostFixture.DEFAULT_THUMBNAIL_URL)
                    .description(PostFixture.DEFAULT_DESCRIPTION)
                    .status(PostStatus.valueOf(PostStatus.PUBLISHED.name()))
                    .createdAt(LocalDateTime.now());
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

    public static class PostWithUserDtoFixture {

        private final PostInfo.PostWithUserDto.PostWithUserDtoBuilder builder;

        private PostWithUserDtoFixture() {
            builder = PostWithUserDto.builder()
                    .post(aPostDto().build())
                    .user(UserInfoFixture.aUserDto().build());
        }

        public PostInfo.PostWithUserDto build() {
            return builder.build();
        }
    }

    public static class PostWithCategoryDtoFixture {

        private final PostInfo.PostWithCategoryDto.PostWithCategoryDtoBuilder builder;

        private PostWithCategoryDtoFixture() {
            CategoryDto cateogry = CategoryInfoFixture.aCategoryDto().build();
            BoardDto board = BoardInfoFixture.aBoardDto().build();
            PostWithUserDto postWithUser = PostInfoFixture.aPostWithUserDto().build();

            builder = PostInfo.PostWithCategoryDto.builder()
                    .postWithUserDto(postWithUser)
                    .board(board)
                    .category(cateogry);
        }

        public PostInfo.PostWithCategoryDto build() {
            return builder.build();
        }
    }
}
