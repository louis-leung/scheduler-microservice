package com.louisleung.springboot.schedulermicroservicecloudconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SchedulerMicroserviceCloudConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerMicroserviceCloudConfigApplication.class, args);
	}
}
