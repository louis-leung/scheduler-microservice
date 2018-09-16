package com.louisleung.springboot.schedulermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SchedulerMicroserviceApplication {

	public static void main(String[] args) {
		//ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		SpringApplication.run(SchedulerMicroserviceApplication.class, args);
	}
}
