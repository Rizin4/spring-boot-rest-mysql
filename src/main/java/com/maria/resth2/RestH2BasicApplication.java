package com.maria.resth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RestH2BasicApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestH2BasicApplication.class, args);
    }
}
