package com.feiwanghub.subcontroller.jdk;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class AopDemo {

    public static class AopDemoException extends RuntimeException {
        public AopDemoException(String message) {
            super(message);
        }
    }

    /**
     * 切面（Aspect）是面向方面编程（AOP）的核心概念。
     * 切面是一个类，它包含与应用程序业务逻辑无关的代码，称为横切关注点。例如，日志记录、事务管理、安全管理等。
     */
    @Aspect
    @Component
    public static class AopLoggingAspect {

        //Pointcut expression 切入点表达式
        //在目标方法抛出异常时执行的通知。可以捕获目标方法抛出的异常，进行异常处理、日志记录等操作。
        @AfterThrowing(pointcut = "execution(* com.feiwanghub.subcontroller.jdk.AopDemo.*(..))", throwing = "ex")
        public void logAfterException(AopDemoException ex) {
            System.out.println("AopDemo: AfterThrowing point cut caught: " + ex.getMessage());
        }

        //在目标方法执行之前执行的通知。通常用于执行一些预处理操作，例如权限检查、参数验证等。
        @Before("execution(* com.feiwanghub.subcontroller.jdk.AopDemo.*(..))")
        public void logBefore() {
            System.out.println("AopDemo Before: point cut caught");
        }

        //在目标方法执行之后（不论是否抛出异常）执行的通知。通常用于执行一些清理操作，例如资源释放、日志记录等。
        @After("execution(* com.feiwanghub.subcontroller.jdk.AopDemo.*(..))")
        public void logAfter() {
            System.out.println("AopDemo After: point cut caught");
        }

        //在目标方法成功执行并返回结果后执行的通知。可以访问目标方法的返回值，并进行一些后续处理，例如日志记录、结果处理等。
        @AfterReturning(pointcut = "execution(* com.feiwanghub.subcontroller.jdk.AopDemo.*(..))", returning = "retVal")
        public void logAfterReturning(Object retVal) {
            System.out.println("AopDemo AfterReturning: point cut caught, return value: " + retVal);
        }

        @Before("com.feiwanghub.subcontroller.jdk.AopPointCutExample.annotationPointcut()")
        public void useExistingPointcut() {
            System.out.println("AopDemo useExistingPointcut: point cut for DummyAnnotation caught");
        }

        //用于定义一个环绕通知（around advice），它可以在方法调用前和方法调用后执行一些特定的操作。
        @Around("execution(* com.feiwanghub.subcontroller.jdk.AopDemo.testAopAround())")
        public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
            System.out.println("AopDemo Around: Before method execution");
            Object result = joinPoint.proceed();
            System.out.println("AopDemo Around: After method execution");
            return result;
        }
    }

    public void testAopAround() {
        System.out.println("AopDemo testAopAround executed");
    }

    public void testAopThrowingLogging() {
        throw new AopDemoException("test exception");
    }

    @AopPointCutExample.DummyAnnotation
    public void testAopAnnotation(){}

    public void testEntryPoint(){
        //this.testAopThrowingLogging();
        //this.testAopAround();
        this.testAopAnnotation();
    }

}
