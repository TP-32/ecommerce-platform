package com.tp32.ecommerceplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommercePlatformApplication {

	public static void main(String[] args) {
		System.getProperties().put("server.port", 5000); // Changes from the default port (8080) to 3000
		SpringApplication.run(EcommercePlatformApplication.class, args);
	}
}
