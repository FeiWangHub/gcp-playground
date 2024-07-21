package com.feiwanghub.subcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 */
@SpringBootApplication
public class AppController {

    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(AppController.class, args);
        //appContext.getBean(AopDemo.class).testEntryPoint();
    }
}