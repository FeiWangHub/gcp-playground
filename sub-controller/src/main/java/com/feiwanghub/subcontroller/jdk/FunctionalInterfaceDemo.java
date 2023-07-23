package com.feiwanghub.subcontroller.jdk;

import java.util.stream.Stream;

/**
 * '@FunctionalInterface' 帮助检查接口是否符合lambda接口定义，标注的接口只能有一个抽象方法
 * 一些JDK提供的函数式接口：
 * java.util.function.Consumer<T>： 接受一个输入参数T，没有返回值。用于执行某些操作，如打印、写入等。
 * java.util.function.Supplier<T>： 不接受任何参数，返回一个结果类型T。用于生成数据或对象。
 * java.util.function.Function<T, R>： 接受一个输入参数T，返回一个结果类型R。用于对输入数据进行处理和转换。
 * java.util.function.Predicate<T>： 接受一个输入参数T，返回一个布尔值。用于对输入数据进行条件判断。
 * java.util.function.BiFunction<T, U, R>： 接受两个输入参数T和U，返回一个结果类型R。用于对两个输入数据进行处理和转换。
 * java.util.function.BinaryOperator<T>： 接受两个相同类型的输入参数T，返回一个相同类型的结果T。用于执行二元操作。
 * java.util.function.UnaryOperator<T>： 接受一个输入参数T，返回一个相同类型的结果T。用于执行一元操作。
 */
public class FunctionalInterfaceDemo {

    @FunctionalInterface
    interface FuncInterface<T> {
        T sayMsg(T msg);
    }

    /* Define a lambda function that, take 1 type A object, and return type B object */
    @FunctionalInterface
    interface FuncABTest<A, B> {
        B apply(A a);
    }

    public static String outputStr(String msg) {
        return "With method reference: " + msg;
    }

    static class Event<T> {
        T data;

        public Event(T data) {
            this.data = data;
        }

        <B> Event<?> transform(FuncABTest<T, B> f) {
            return new Event<B>(f.apply(data));
        }
    }

    public static void main(String[] args) {
        //1. use lambda expression
        FuncInterface<String> func = msg -> "With lambda expression: " + msg;
        System.out.println(func.sayMsg("Hello World"));

        //2. use method reference
        FuncInterface<String> func2 = FunctionalInterfaceDemo::outputStr;
        System.out.println(func2.sayMsg("Hello World"));

        //3. mock stream api
        Stream<Event<Integer>> s = Stream.of(
                new Event<>(11),
                new Event<>(22),
                new Event<>(33)
        );
        s.map(event -> event.transform(String::valueOf))
                .forEach(event -> System.out.println(event.data));

        //4. mock square function
        FuncABTest<Integer, Integer> square = x -> x * x;
        System.out.println("Mock Square function: " + square.apply(5));
    }
}
