package com.nubble.backend.utils.customassert;

import com.nubble.backend.post.service.PostInfo;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class PostDtoAssert extends AbstractAssert<PostDtoAssert, PostInfo.PostDto> {

    private PostDtoAssert(PostInfo.PostDto actual) {
        super(actual, PostDtoAssert.class);
        isNotNull();
    }

    public static PostDtoAssert assertThat(PostInfo.PostDto actual) {
        return new PostDtoAssert(actual);
    }

    public void isEqualTo(PostInfo.PostDto expected) {
        assertThat(expected).isNotNull();
        Assertions.assertThat(actual).usingRecursiveComparison()
                .withStrictTypeChecking().isEqualTo(expected);
    }
}
