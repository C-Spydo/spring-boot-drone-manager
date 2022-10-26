package com.cspydo.dronemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DronemanagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(DronemanagerApplication.class, args);
	}
}
