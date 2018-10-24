package ru.asartamonov.oauth_security.controller;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TechController {
    @GetMapping("/error")
    public String errorGet() {
        return "error";
    }
}
