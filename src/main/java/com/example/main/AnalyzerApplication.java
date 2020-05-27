package com.example.main;

import com.example.controller.Manager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyzerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(AnalyzerApplication.class, args);
		Manager manager = new Manager();
		manager.testResult();
	}

}
