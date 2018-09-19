package com.louisleung.springboot.schedulermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@RefreshScope
public class SchedulerMicroserviceApplication {
	/*
	@Bean
	ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}
*/
	public static void main(String[] args) {
		//ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		SpringApplication.run(SchedulerMicroserviceApplication.class, args);
	}
}
