package com.feiwanghub.subcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(App.class, args);
        //appContext.getBean(AopDemo.class).testEntryPoint();
    }
}