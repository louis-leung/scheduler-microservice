package com.louisleung.springboot.schedulermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class SchedulerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerMicroserviceApplication.class, args);
	}
}
