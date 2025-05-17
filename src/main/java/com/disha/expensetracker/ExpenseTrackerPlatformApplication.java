package com.disha.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.disha.expensetracker")
public class ExpenseTrackerPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerPlatformApplication.class, args);
	}

}
