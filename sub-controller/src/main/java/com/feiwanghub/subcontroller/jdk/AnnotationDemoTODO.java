package com.feiwanghub.subcontroller.jdk;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.lang.annotation.*;
import java.lang.reflect.Method;

@SupportedAnnotationTypes("com.feiwanghub.subcontroller.jdk.AnnotationDemo.AnnoDemo")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class AnnotationDemoTODO {

    @Documented //appear in java doc
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
    public @interface AnnoDemo {
        String value() default "hello";

        int priority() default 0;
    }

    @AnnoDemo(value = "six six six", priority = 666)
    public String annoDemoMethod() {
        return "annotatedMethod";
    }

    public void readAnnotatedMethodByReflection() {
        Method[] methods = AnnotationDemoTODO.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(AnnoDemo.class)) {
                AnnoDemo annoDemo = method.getAnnotation(AnnoDemo.class);
                System.out.println("Method annotated: " + method.getName());
                System.out.println("Annotation value: " + annoDemo.value());
                System.out.println("Annotation priority: " + annoDemo.priority());
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface MyNotNull {
    }

//    public void testMyNotNullAnno(@MyNotNull AnnotationDemoTODO name) {
//        validateNotNull(this);
//    }

//    public static void validateNotNull(Object obj) {
//        Method[] methods = obj.getClass().getDeclaredMethods();
//        for (Method method : methods) {
//            if (method.getParameterCount() > 0) {
//                for (int i = 0; i < method.getParameterCount(); i++) {
//                    if (method.getParameters()[i].isAnnotationPresent(MyNotNull.class)) {
//                        Object paramValue = method.getParameters()[i];
//                        if (paramValue == null) {
//                            throw new IllegalArgumentException("param " + i + " is null");
//                        }
//                    }
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
        AnnotationDemoTODO annotationDemo = new AnnotationDemoTODO();
        annotationDemo.readAnnotatedMethodByReflection();
//        annotationDemo.testMyNotNullAnno(null);
    }

}
