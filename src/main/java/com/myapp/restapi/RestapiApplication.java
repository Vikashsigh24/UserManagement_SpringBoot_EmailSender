package com.myapp.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableAsync
public class RestapiApplication{

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);		
		

		System.out.println("Server is running on port 8080...............");
		System.out.println("Thread Name: " + Thread.currentThread().getName());

	}

}
