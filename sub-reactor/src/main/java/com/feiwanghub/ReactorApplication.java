package com.feiwanghub;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorApplication {

    public static void main(String[] args) {
//        fluxDemo();
        monoDemo();
    }

    public static void fluxDemo() {
        Flux.just("Hello", "World")
            .subscribe(System.out::println);

        Flux<String> fluxStr = Flux.just("Hello", "World", "from", "Reactor!");
        fluxStr.subscribe(System.out::println);
    }

    public static void monoDemo() {
        Mono<String> monoStr = Mono.just("Mono Demo from Reactor!");
        monoStr.subscribe(System.out::println);

        // Transforming Mono
        Mono<String> transformMapMono = Mono.just("Mono Transform Map Demo");
        transformMapMono.map(String::toUpperCase)
            .subscribe(System.out::println);

        // Error handling
        Mono<String> monoExceptionDemo = Mono.just("Mono Exception Demo");
        monoExceptionDemo.map(s -> {
                if (s.equals("Mono Exception Demo")) {
                    throw new RuntimeException("DEMO: Mono Exception Occured!");
                }
                return s.toUpperCase();
            })
            .onErrorReturn("DEMO: Error occurred, return fallback value!")
            .subscribe(System.out::println);

        // Error handling with onErrorMap
        monoExceptionDemo.map(s -> {
                if (s.equals("Mono Exception Demo")) {
                    throw new RuntimeException("DEMO: Mono Exception Occured!");
                }
                return s.toUpperCase();
            })
            .onErrorMap(e -> new Exception("Custom error occurred!"))
            .subscribe(System.out::println, error -> {
                System.out.println("DEMO: Error occurred, msg is: " + error.getMessage());
            });

        // Zip Mono
        Mono<String> mono1 = Mono.just("Zip1");
        Mono<String> mono2 = Mono.just("Zip2");
        Mono.zip(mono1, mono2)
            .map(tuple -> "Mono Zip test: " + tuple.getT1() + " + " + tuple.getT2())
            .subscribe(System.out::println);
    }
}