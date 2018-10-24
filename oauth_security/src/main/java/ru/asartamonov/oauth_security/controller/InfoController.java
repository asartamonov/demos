package ru.asartamonov.oauth_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class InfoController {

    @GetMapping("/getinfo")
    public Principal getInfo(Principal principal) {
        return principal;
    }
}
