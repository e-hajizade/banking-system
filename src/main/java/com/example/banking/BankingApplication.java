package com.example.banking;

import com.example.banking.service.ConsoleInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ApplicationContext context) {
		return args -> {
			ConsoleInterface consoleInterface = context.getBean(ConsoleInterface.class);
			consoleInterface.start();
		};
	}
}
