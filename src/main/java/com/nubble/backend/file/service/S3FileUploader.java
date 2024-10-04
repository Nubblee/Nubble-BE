package com.nubble.backend.file.service;

import com.nubble.backend.config.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3FileUploader implements FileUploader {

    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    @Override
    public void upload(String fileName, byte[] file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsProperties.getBucketName())
                .key(fileName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
    }

    @Override
    public String getBaseUrl() {
        return "https://" + awsProperties.getBucketName() + ".s3.amazonaws.com/";
    }
}
