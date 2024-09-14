package com.example.task_manegement_web_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TaskManegementWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(TaskManegementWebApplication.class, args);
    }

}
