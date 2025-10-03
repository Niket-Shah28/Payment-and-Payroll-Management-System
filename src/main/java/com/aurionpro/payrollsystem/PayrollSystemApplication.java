package com.aurionpro.payrollsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.aurionpro.payrollsystem.entity")
public class PayrollSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayrollSystemApplication.class, args);
	}

}
