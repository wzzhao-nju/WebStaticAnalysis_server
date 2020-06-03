package com.example.main;

import com.example.controller.Manager;
import com.example.json.Defect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.Vector;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.example.*")
public class AnalyzerApplication {

	public static void main(String[] args) {
		/*Vector<Defect> defects = new Vector<>();
		Defect d1 = new Defect();
		d1.setLocation("111.cpp:25:10");
		d1.setInfo("有问题啦");
		Defect d2 = new Defect();
		d2.setLocation("111.cpp:25:16");
		d2.setInfo("也有问题");
		defects.add(d1);
		defects.add(d2);
		Manager manager = new Manager();
		manager.merge(defects);*/
		SpringApplication.run(AnalyzerApplication.class, args);
	}

}
