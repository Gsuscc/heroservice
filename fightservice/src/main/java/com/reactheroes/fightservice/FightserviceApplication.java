package com.reactheroes.fightservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FightserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FightserviceApplication.class, args);
	}

}
