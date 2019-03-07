package com.url.shortener.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan(basePackages = { "com.url.shortener.urlshortener.model" })
@EnableJpaRepositories(basePackages = { "com.url.shortener.urlshortener.repository" })
@SpringBootApplication
@EnableSwagger2
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

}
