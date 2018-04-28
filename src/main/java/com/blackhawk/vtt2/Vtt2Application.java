package com.blackhawk.vtt2;

import com.blackhawk.vtt2.util.HashPasswordEncoder;
import javafx.application.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Vtt2Application extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public HashPasswordEncoder hashPasswordEncoder() {
        return new HashPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(Vtt2Application.class, args);
    }
}
