package com.gabi.career_navigator_was;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CareerNavigatorWasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerNavigatorWasApplication.class, args);
	}

}
