package com.dreyer.bradescocodechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BradescoCodeChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BradescoCodeChallengeApplication.class, args);
	}

}
