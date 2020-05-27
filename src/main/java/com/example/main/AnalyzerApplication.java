package com.example.main;

import com.example.controller.Manager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyzerApplication {

	public static void main(String[] args) {
		System.out.print("main start\n");
		Manager manager = new Manager();
		manager.testResult();
		SpringApplication.run(AnalyzerApplication.class, args);
	}

}
