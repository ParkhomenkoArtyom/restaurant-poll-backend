package com.backend.RestaurantPoll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RestaurantPollApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestaurantPollApplication.class, args);
	}
}
