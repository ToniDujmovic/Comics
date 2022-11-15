package com.example.comicsbe;

import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ComicsBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComicsBeApplication.class, args);
    }


}
