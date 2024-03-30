package com.api.EngineerCollabo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EngineerCollaboApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngineerCollaboApplication.class, args);
	}

}
