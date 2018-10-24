package ru.asartamonov.oauth_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
public class OauthSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthSecurityApplication.class, args);
    }
}
