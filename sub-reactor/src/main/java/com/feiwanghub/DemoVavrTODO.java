package com.feiwanghub;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;

public class DemoVavrTODO {

    public static void main(String[] args) {
//        testOption();
//        testEither();
        testTryOf();
    }

    //test Option
    public static void testOption() {
        // 创建一个Option实例
        Option<String> name = Option.of(getNullString());

        // 使用getOrElse()获取值，如果没有则返回默认值
        String nameOrDefault = name.getOrElse("FallbackDefault");
        System.out.println("Name: " + nameOrDefault);

        // 使用ifPresent()仅当存在值时执行操作
        name.forEach(System.out::println);
    }

    // 模拟一个可能返回null的方法
    private static String getNullString() {
        return null;
    }

    public static void testEither() {
        // Either它代表了一个值可以是两种类型之一的情况。
        // Either通常用于表示操作的结果可能是成功（Right）或者失败（Left）。
        Either<String, Integer> success = divide(10, 2);
        Either<String, Integer> failure = divide(10, 0);

        System.out.println("Either:" + success); // Right(5)
        System.out.println("Either:" + failure); // Left(Division by zero)
    }

    public static Either<String, Integer> divide(int dividend, int divisor) {
        if (divisor == 0) {
            return Either.left("Division by zero");
        } else {
            return Either.right(dividend / divisor);
        }
    }

    //Try 类型有两个子类型：Success 和 Failure。Success 表示操作成功，并包含结果值；Failure 表示操作失败，并包含异常。
    public static void testTryOf() {
        // 一个可能抛出异常的操作
        Try<Integer> result = Try.of(() -> divideWithException(10, 2));
        Try<Integer> failure = Try.of(() -> divideWithException(10, 0));

        // 处理成功和失败的结果
        result
            .onSuccess(value -> System.out.println("TryOf Success: " + value)) // 成功时的处理
            .onFailure(ex -> System.out.println("TryOf Error: " + ex.getMessage())); // 失败时的处理

        failure
            .onSuccess(value -> System.out.println("TryOf Success: " + value))
            .onFailure(ex -> System.out.println("TryOf Error: " + ex.getMessage()));
    }

    // A divide function that throws an exception
    public static int divideWithException(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return dividend / divisor;
    }

}
