package com.itau.investments.investmentcontrolsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class InvestmentControlSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestmentControlSystemApplication.class, args);
	}

}
