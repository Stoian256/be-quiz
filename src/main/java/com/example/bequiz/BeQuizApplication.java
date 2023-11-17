package com.example.bequiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing()
public class BeQuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeQuizApplication.class, args);
    }

}
