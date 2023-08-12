package com.feiwanghub.subcontroller.jdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class AopDemoTest {

    @Resource
    private AopDemo aopDemo;

    @Test
    public void testAopLogging(){
        aopDemo.testAopThrowingLogging();
    }

    @Test
    public void testAopAround(){
        aopDemo.testAopAround();
    }

}