package com.Soo_Shinsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SooShinsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SooShinsaApplication.class, args);
	}

}
