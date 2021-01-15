package com.offcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class WebStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStartApplication.class,args);
    }
}
