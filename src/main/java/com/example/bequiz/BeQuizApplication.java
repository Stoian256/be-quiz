package com.example.bequiz;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
@SecurityScheme(name = "be_quiz_auth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer",description = "JWT Authorization header using the Bearer scheme.")
public class BeQuizApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeQuizApplication.class, args);
    }
}

