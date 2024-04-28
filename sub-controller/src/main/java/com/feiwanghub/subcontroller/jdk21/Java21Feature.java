package com.feiwanghub.subcontroller.jdk21;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Reference wiki
 * 1. <a href="https://www.happycoders.eu/java/java-21-features/">HappyCoder link</a>
 * 2. <a href="https://blog.csdn.net/u010398771/article/details/133127060">CSDN link</a>
 */
public class Java21Feature {

    void tryStringTemplate() {
        String productName = "Widget";
        double productPrice = 29.99;
        boolean productAvailable = true;

        String productInfo = """
            Product name: %s
            Product price: %.2f
            Product available: %b
            """.formatted(productName, productPrice, productAvailable);

        System.out.println(productInfo);
    }

    void trySequencedCollection() {
        List<String> names = List.of("1st", "2nd", "3rd");
        System.out.println("getFirst: " + names.getFirst());
        System.out.println("getLast: " + names.getLast());
    }

//        TODO deconstruct
//    void tryRecordPatternDeconstruct(Object obj) {
//        if(obj instanceof Point21(int x, int y)) {
//            System.out.println(obj.x());
//            System.out.println(obj.y());
//        }
//        if(obj instanceof RecordJava21) {
//            System.out.println(obj.x());
//            System.out.println(obj.y());
//        }
//    }

//    record RecordJava21(int x, int y) { }

    //TODO
    void tryVirtualThread() {
        Runnable runnable = () -> System.out.println("Inside Runnable");
        Thread.startVirtualThread(runnable);
        Thread virtualThread = Thread.ofVirtual().start(runnable);
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        executor.submit(runnable);
    }

    void tryPatternMatchingForSwitch() {
        Object obj = "test";
        String formatted;

        // Prior to Java 21
        if (obj instanceof Integer i) {
            formatted = String.format("int %d", i);
        } else if (obj instanceof Long l) {
            formatted = String.format("long %d", l);
        } else if (obj instanceof Double d) {
            formatted = String.format("double %f", d);
        } else if (obj instanceof String s) {
            formatted = String.format("String %s", s);
        }

        // As of Java 21
        formatted = switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case String s -> String.format("String %s", s);
            default -> "Unknown";
        };
        System.out.printf("tryPatternMatchingForSwitch result is: %s", formatted);
    }

    public static void main(String[] args) {
        Java21Feature java21Feature = new Java21Feature();
        java21Feature.tryStringTemplate();
        java21Feature.trySequencedCollection();
        java21Feature.tryPatternMatchingForSwitch();

        java21Feature.tryVirtualThread();
    }

}
