package com.dreyer.bradescocodechallenge.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.dreyer.bradescocodechallenge.business.usecase"
})
public class UseCaseConfig {
}
