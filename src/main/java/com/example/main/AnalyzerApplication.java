package com.example.main;

import com.example.controller.Manager;
import com.example.json.Defect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.sql.Timestamp;
import java.util.Vector;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.example.*")
@EnableAsync
public class AnalyzerApplication {

	public static void main(String[] args) {
		
		//SpringApplication.run(AnalyzerApplication.class, args);
	}

}
