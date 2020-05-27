package com.example.main;

import com.example.controller.Manager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AnalyzerApplication {

	public static void main(String[] args) {
		//Manager manager = new Manager();
		//manager.getResult("test.cpp");
		SpringApplication.run(AnalyzerApplication.class, args);
	}

}
