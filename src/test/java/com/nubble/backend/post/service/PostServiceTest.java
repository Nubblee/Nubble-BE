package com.nubble.backend.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostPublishCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.util.Optional;
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

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("게시글을 생성한다.")
    @Test
    void createPost_success() {
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        // given
        PostCreateCommand command = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .build();

        // when
        long newPostId = postService.createPost(command);

        // then
        Optional<Post> postOptional = postRepository.findById(newPostId);

        assertThat(postOptional).isPresent();
        assertThat(postOptional.get().getId()).isEqualTo(newPostId);
        assertThat(postOptional.get().getTitle()).isEqualTo(command.title());
        assertThat(postOptional.get().getContent()).isEqualTo(command.content());
        assertThat(postOptional.get().getUser().getId()).isEqualTo(user.getId());
        assertThat(postOptional.get().getStatus()).isEqualTo(PostStatus.DRAFT);
        assertThat(postOptional.get().getThumbnailUrl()).isNull();
        assertThat(postOptional.get().getDescription()).isNull();
    }

    @DisplayName("게시글의 주인이 게시글을 게시합니다.")
    @Test
    void test() {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        PostCreateCommand postCreateCommand = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .build();
        long postId = postService.createPost(postCreateCommand);

        PostPublishCommand postPublishCommand = PostPublishCommand.builder()
                .userId(user.getId())
                .postId(postId)
                .thumbnailUrl("https://example.com/thumbnail.jpg")
                .description("설명입니다.")
                .build();

        // when
        PostInfo postInfo = postService.publishPost(postPublishCommand);

        // then
        assertThat(postInfo.postId()).isEqualTo(postId);
        assertThat(postInfo.title()).isEqualTo(postCreateCommand.title());
        assertThat(postInfo.content()).isEqualTo(postCreateCommand.content());
        assertThat(postInfo.userId()).isEqualTo(user.getId());
        assertThat(postInfo.thumbnailUrl()).isEqualTo(postPublishCommand.thumbnailUrl());
        assertThat(postInfo.description()).isEqualTo(postPublishCommand.description());
        assertThat(postInfo.postStatus()).isEqualTo("PUBLISHED");
    }
}
