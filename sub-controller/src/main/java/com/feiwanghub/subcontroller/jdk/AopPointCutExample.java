package com.feiwanghub.subcontroller.jdk;

import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1. execution：用于匹配方法的执行。示例：
 * execution(public * com.example.service.*.*(..))：匹配 com.example.service 包中所有公共方法的执行。
 * 2. within：用于匹配指定类型的所有方法。示例：
 * within(com.example.service.*)：匹配 com.example.service 包中所有类的所有方法。
 * 3. this：用于匹配实现了指定接口的代理对象方法调用。示例：
 * this(com.example.MyInterface)：匹配实现了 com.example.MyInterface 接口的对象的方法调用。
 * 4. target：用于匹配被代理的目标对象方法调用。示例：
 * target(com.example.MyClass)：匹配目标对象为 com.example.MyClass 类的方法调用。
 * 5. args：用于匹配方法参数类型。示例：
 * args(String)：匹配有一个 String 参数的方法调用。
 * 6. @annotation：用于匹配被指定注解标注的方法。示例：
 * '@annotation(com.example.MyAnnotation)'：匹配被 com.example.MyAnnotation 注解标注的方法。
 * 7. bean：用于匹配指定名称的 Bean 方法调用。示例：
 * bean(myService)：匹配名称为 myService 的 Bean 的方法调用。
 */
public class AopPointCutExample {

    //1. execution：用于匹配方法的执行
    @Pointcut("execution(public * com.feiwanghub.subcontroller.jdk.AopPointCutExample.dummy())")
    public void executionPointcut() {}

    // 2. within：用于匹配指定类型的所有方法
    @Pointcut("within(com.feiwanghub.subcontroller.jdk.AopPointCutExample)")
    public void withinPointcut() {}

    //3. this：用于匹配实现了指定接口的代理对象方法调用。
    @Pointcut("this(com.feiwanghub.subcontroller.jdk.AopPointCutExample.DummyInterface)")
    public void thisPointcut() {}

    //4. target：用于匹配被代理的目标对象方法调用。
    @Pointcut("target(com.feiwanghub.subcontroller.jdk.AopPointCutExample)")
    public void targetPointcut() {}

    //5. args：用于匹配方法参数类型。
    @Pointcut("args(String)")
    public void argsPointcut() {}

    //6. @annotation：用于匹配被指定注解标注的方法。
    @Pointcut("@annotation(com.feiwanghub.subcontroller.jdk.AopPointCutExample.DummyAnnotation)")
    public void annotationPointcut() {}

    //7. bean：用于匹配指定名称的 Bean 方法调用。
    @Pointcut("bean(dummyBean)")
    public void beanPointcut() {}

    public void dummy() {}

    public interface DummyInterface {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DummyAnnotation {}

}
