package com.car.manager.api.storage.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${com.car.manager.storage.aws.s3.region}")
    private String region;

    @Bean
    public S3Client amazonS3() {
        return S3Client.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("java-car-manager"))
                .region(Region.EU_WEST_1)
                .build();
    }


}
