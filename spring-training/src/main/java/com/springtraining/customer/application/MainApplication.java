package com.springtraining.customer.application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication 
@ComponentScan({"com.springtraining.customer.*"})


public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class , args);

	}

}
