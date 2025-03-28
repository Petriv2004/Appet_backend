package com.unipiloto.APPET_FT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AppetFtApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppetFtApplication.class, args);
	}
}
