package com.nubble.backend.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

@Disabled("개발 중 S3 연결 테스트는 필요하지 않기 때문에 비활성화됨. 필요시 주석 제거 후 실행")
@SpringBootTest
class AwsConfigTest {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private AwsProperties awsProperties;

    @DisplayName("S3 연결 및 버킷 존재를 확인합니다.")
    @Test
    void connect_s3() {
        // when
        ListBucketsResponse actual = s3Client.listBuckets();

        // then
        boolean bucketExists = actual.buckets().stream()
                .map(Bucket::name)
                .anyMatch(name -> name.equals(awsProperties.getBucketName()));
        assertThat(bucketExists).isTrue();
    }
}
