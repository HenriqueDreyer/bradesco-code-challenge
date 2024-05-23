package com.dreyer.bradescocodechallenge.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.dreyer.bradescocodechallenge.business.gateway")
@EnableJpaRepositories(basePackages = "com.dreyer.bradescocodechallenge.infra.jpa.repository")
@EntityScan(basePackages = {"com.dreyer.bradescocodechallenge.infra.jpa.entity", "com.dreyer.bradescocodechallenge.business.gateway"})
public class PersistenceConfig {
}
