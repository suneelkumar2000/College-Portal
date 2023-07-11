package com.project.college_portal.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MarchDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarchDemoApplication.class, args);
	}

}
