package com.reactheroes.heroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class HeroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeroserviceApplication.class, args);
	}

}
