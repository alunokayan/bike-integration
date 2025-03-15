package br.edu.ifsp.spo.bike_integration.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

@Configuration
public class AwsConfig {

    @Value("${aws.profile}")
    private String profile;

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return ProfileCredentialsProvider.create(this.profile);
    }

}