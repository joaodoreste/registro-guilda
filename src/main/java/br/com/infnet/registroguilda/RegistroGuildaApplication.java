package br.com.infnet.registroguilda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RegistroGuildaApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistroGuildaApplication.class, args);
    }
}