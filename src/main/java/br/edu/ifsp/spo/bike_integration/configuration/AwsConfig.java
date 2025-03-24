package br.edu.ifsp.spo.bike_integration.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.common.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

@Configuration
public class AwsConfig {

    @Value("${aws.profile}")
    private String profile;

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        // Imprime o profile
        System.out.println("AWS Profile: " + profile);

        AwsCredentialsProvider credentialsProvider;
        if (StringUtils.isBlank(profile) || "default".equals(profile)) {
            System.out.println(">>>>>>>>>> Usando profile em branco");
            credentialsProvider = DefaultCredentialsProvider.create();
        } else {
            System.out.println("<<<<<<<<<< Temos profile");
            credentialsProvider = ProfileCredentialsProvider.create(this.profile);
        }

        // Resolve as credenciais sem forçar o cast para AwsSessionCredentials
        AwsCredentials credentials = credentialsProvider.resolveCredentials();
        System.out.println("Access Key: " + credentials.accessKeyId());
        System.out.println("Secret Key: " + credentials.secretAccessKey());

        if (credentials instanceof AwsSessionCredentials) {
            AwsSessionCredentials sessionCredentials = (AwsSessionCredentials) credentials;
            System.out.println("Session Token: " + sessionCredentials.sessionToken());
        } else {
            System.out.println("Session Token: Não disponível");
        }

        return credentialsProvider;
    }
}