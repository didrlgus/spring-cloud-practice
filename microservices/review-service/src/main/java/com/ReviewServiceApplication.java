package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class ReviewServiceApplication {

    private static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "/root/gabia-library-config/review-service.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReviewServiceApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
