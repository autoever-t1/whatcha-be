package com.example.whatcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.example.whatcha")
public class WhatchaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatchaApplication.class, args);
    }

}
