package com.iurii.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@EnableMBeanExport
@EnableWebMvc
public class UserServiceStarter {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceStarter.class, args);
	}
}
