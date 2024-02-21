package com.oauth.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.oauth.web.repository")
@ComponentScan({"com.oauth.web.controller", "com.oauth.web.service",
        "com.oauth.web.config", "com.oauth.web.security"})
@EntityScan("com.oauth.web.model")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}