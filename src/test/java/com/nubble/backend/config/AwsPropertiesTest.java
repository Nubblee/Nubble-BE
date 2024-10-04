package com.nubble.backend.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AwsPropertiesTest {

    @Autowired
    private AwsProperties awsProperties;

    @DisplayName("AWS 설정 값들이 모두 할당되었습니다.")
    @Test
    void assign_values() {
        // then
        assertAll(
                () -> assertThat(awsProperties.getAccessKey()).isNotNull(),
                () -> assertThat(awsProperties.getSecretKey()).isNotNull(),
                () -> assertThat(awsProperties.getRegion()).isNotNull(),
                () -> assertThat(awsProperties.getRegion()).isNotNull()
        );
    }
}
