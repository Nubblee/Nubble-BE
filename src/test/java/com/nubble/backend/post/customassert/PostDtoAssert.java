package com.nubble.backend.post.customassert;

import com.nubble.backend.post.feature.PostDto;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class PostDtoAssert extends AbstractAssert<PostDtoAssert, PostDto> {

    private PostDtoAssert(PostDto actual) {
        super(actual, PostDtoAssert.class);
        isNotNull();
    }

    public static PostDtoAssert assertThat(PostDto actual) {
        return new PostDtoAssert(actual);
    }

    public void isEqualTo(PostDto expected) {
        assertThat(expected).isNotNull();
        Assertions.assertThat(actual).usingRecursiveComparison()
                .withStrictTypeChecking().isEqualTo(expected);
    }
}
