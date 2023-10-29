package com.N2O2.Nitrouz_Studioz.model.aws_s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class s3Config {

    @Value("${aws.region}")
    private String region;

    public S3Client s3Client(){
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .build();
        return s3Client;
    }
}
