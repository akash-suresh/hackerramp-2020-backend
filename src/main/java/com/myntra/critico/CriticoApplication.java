package com.myntra.critico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CriticoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CriticoApplication.class, args);
	}
}
