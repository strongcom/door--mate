package com.doormate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
public class DoormateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoormateApplication.class, args);
	}

}
