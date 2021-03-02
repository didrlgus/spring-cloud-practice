package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ZullApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(ZullApiGateway.class, args);
    }
}
