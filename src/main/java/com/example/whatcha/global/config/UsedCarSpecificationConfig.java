package com.example.whatcha.global.config;

import com.example.whatcha.domain.usedCar.dao.UsedCarSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsedCarSpecificationConfig {

    @Bean
    public UsedCarSpecification usedCarSpecification() {
        return new UsedCarSpecification();
    }
}