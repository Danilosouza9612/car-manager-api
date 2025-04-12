package com.car.manager.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.car.manager.repository.db")
@EntityScan("com.car.manager.repository.schema")
public class CarManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarManagerApplication.class, args);
	}

}
