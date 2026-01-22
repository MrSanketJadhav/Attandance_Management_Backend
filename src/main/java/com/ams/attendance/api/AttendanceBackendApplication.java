package com.ams.attendance.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AttendanceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceBackendApplication.class, args);
		System.out.println("Application started successfully...!");
	}

}
