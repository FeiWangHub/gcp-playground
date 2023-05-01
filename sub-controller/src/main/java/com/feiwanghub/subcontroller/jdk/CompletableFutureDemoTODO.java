package com.feiwanghub.subcontroller.jdk;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemoTODO {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testSimpleCompletableFuture();
    }

    //串行执行2个sync测试
    public static void testSimpleCompletableFuture() throws InterruptedException, ExecutionException {
        // 第一个任务:
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> queryCode("中国石油"));
        cfQuery.thenAccept((e) -> System.out.println("code:" + e));

        // cfQuery成功后继续执行下一个任务: (thenApplyAsync串行化处理另一个CompletableFuture)
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> fetchPrice(code));
        cfFetch.thenAccept(result -> System.out.println("price: " + result));// cfFetch成功后打印结果

        //cfFetch.get(); //get()函数会阻塞，主线程等待cfFetch执行完毕
        System.out.println("main thread end");
        Thread.sleep(2000); //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
    }

    static String queryCode(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        return "601857";
    }

    static Double fetchPrice(String code) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        return 5 + Math.random() * 20;
    }

}
