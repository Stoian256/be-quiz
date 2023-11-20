package com.example.bequiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BeQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeQuizApplication.class, args);
	}

}
