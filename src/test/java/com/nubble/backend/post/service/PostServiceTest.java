package com.nubble.backend.post.service;

import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @DisplayName("게시글을 생성한다.")
    @Test
    void createPost_success() {
        // given
        PostCreateCommand command = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        long newPostId = postService.createPost(command);

        // then
        // todo 게시글 저장소에 command 내용으로 작성된 게시글이 저장되어 있어야 한다.
    }
}
