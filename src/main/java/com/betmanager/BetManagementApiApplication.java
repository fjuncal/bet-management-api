package com.betmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BetManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetManagementApiApplication.class, args);
	}

}
