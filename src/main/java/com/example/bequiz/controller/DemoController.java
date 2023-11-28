package com.example.bequiz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('Admin')")
@SecurityRequirement(name = "be_quiz_auth")
public class DemoController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world!";
    }
}
