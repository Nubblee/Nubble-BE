package com.nubble.backend.utils.customassert;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class PostAssert extends AbstractAssert<PostAssert, Post> {

    private PostAssert(Post actual) {
        super(actual, PostAssert.class);
        isNotNull();
    }

    public static PostAssert assertThat(Post actual) {
        return new PostAssert(actual);
    }

    public PostAssert hasId(Long expectedId) {
        Assertions.assertThat(actual.getId()).isEqualTo(expectedId);
        return this;
    }

    public PostAssert hasTitle(String expectedTitle) {
        Assertions.assertThat(actual.getTitle()).isEqualTo(expectedTitle);
        return this;
    }

    public PostAssert hasContent(String expectedContent) {
        Assertions.assertThat(actual.getContent()).isEqualTo(expectedContent);
        return this;
    }

    public PostAssert hasUserId(Long expectedUserId) {
        Assertions.assertThat(actual.getUser().getId()).isEqualTo(expectedUserId);
        return this;
    }

    public PostAssert hasStatus(PostStatus expectedStatus) {
        Assertions.assertThat(actual.getStatus()).isEqualTo(expectedStatus);
        return this;
    }

    public PostAssert hasThumbnailUrl(String expectedThumbnailUrl) {
        Assertions.assertThat(actual.getThumbnailUrl()).isEqualTo(expectedThumbnailUrl);
        return this;
    }

    public PostAssert hasDescription(String expectedDescription) {
        Assertions.assertThat(actual.getDescription()).isEqualTo(expectedDescription);
        return this;
    }
}
